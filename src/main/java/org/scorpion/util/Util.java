package org.scorpion.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.scorpion.util.file.FileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lukas on 11/15/2021
 */
public class Util {

    public final static String VERSION = "1.2";
    protected static final LinkedList<String> warps = new LinkedList<>();
    protected static final HashMap<Player, Player> tpa = new HashMap<>();
    protected static final HashMap<Player, Player> tpaHere = new HashMap<>();
    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String getColorCode(String input) {
        Matcher match = pattern.matcher(input);
        while (match.find()) {
            String color = input.substring(match.start(), match.end());
            input = input.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(input);
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void manageLang() {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        FileManager de = new FileManager("plugins/HoneyCore/Lang/de.yml");
        FileManager en = new FileManager("plugins/HoneyCore/Lang/en.yml");

        if (!file.exist()) {
            file.set("language", "en");
            file.set("prefix", "&8[&eHoney&6Core&8]");
            file.set("hide-server-detail", true);
            file.set("permission.gamemode", "honey.gamemode");
            file.set("permission.gamemode-target", "honey.gamemode.target");
            file.set("permission.heal", "honey.heal");
            file.set("permission.heal-target", "honey.heal.target");
            file.set("permission.setspawn", "honey.setspawn");
            file.set("permission.admin", "honey.admin");
            file.set("permission.tp", "honey.tp");
            file.set("permission.enderchest-target", "honey.enderchest.target");
            file.set("permission.fly", "honey.fly");
        }

        if (!de.exist()) {
            de.set("message.gamemode", "%prefix% &6Dein Spielmodus ist jetzt auf &c%gm%&6!");
            de.set("message.gamemode-target", "%prefix% &6Der Spielmodus von &4%target% &6ist jetzt auf &c%gm%&6!");
            de.set("message.player-not-found", "%prefix% &c%target% ist nicht online!");
            de.set("message.heal", "%prefix% &2Du bist nun &ageheilt&2!");
            de.set("message.heal-target", "%prefix% &2Du hast &a%target% &2geheilt!");
            de.set("message.spawn-set", "%prefix% &2Spawn wurde gesetzt.");
            de.set("message.spawn-teleport", "%prefix% &2Du wurdest zum Spawn teleportiert.");
            de.set("message.no-permission", "%prefix% &cDu hast nicht die benötigte Rechte um es zu tun.");
        }

        if (!en.exist()) {
            en.set("message.gamemode", "%prefix% &6Your gamemode changed to &c%gm%&6!");
            en.set("message.gamemode-target", "%prefix% &6The gamemode from &4%target% &6changed to &c%gm%&6!");
            en.set("message.player-not-found", "%prefix% &c%target% is not online!");
            en.set("message.heal", "%prefix% &2You are now &ahealed&2!");
            en.set("message.heal-target", "%prefix% &2You have healed &a%target%&2!");
            en.set("message.spawn", "%prefix% &2Set spawn.");
            en.set("message.spawn-set", "%prefix% &2Set spawn.");
            en.set("message.spawn-teleport", "%prefix% &2You are teleported to spawn.");
            en.set("message.no-permission", "%prefix% &cYou have not the permission to do that.");
        }

        file.save();
        de.save();
        en.save();
    }

    public static void reloadWarps() {
        warps.clear();
        initWarp();
    }

    public static LinkedList<String> getWarps() {
        return warps;
    }

    public static void initWarp() {
        File file = new File("plugins/HoneyCore/warp/");
        File[] files = file.listFiles();
        if (files != null) {
            for (File object : files) {
                warps.add(object.getName().replace(".yml", ""));
            }
        }
    }

    private static void saveRandomTPActionTime(UUID uuid) {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid + ".yml");
        file.set("rtp", System.currentTimeMillis() + Time.MINUTE.getTime() * 5);
        file.save();
    }

    private static boolean canRTP(UUID uuid) {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid + ".yml");
        return System.currentTimeMillis() > file.getLong("rtp");
    }

    public static void randomTeleportPlayer(Player p) {
        if (!p.hasPermission("api.rtp.bypass")) {
            if (canRTP(p.getUniqueId())) {
                p.sendMessage(getPrefix() + "");
                return;
            }
            saveRandomTPActionTime(p.getUniqueId());
        }
        p.teleport(generateRandomLocation());
    }

    @NotNull
    private static Location generateRandomLocation() {
        var world = Bukkit.getWorld("world");
        Random random = new Random();
        int x = random.nextInt(4000) + 200;
        int y = 150;
        int z = random.nextInt(4000) + 200;
        Location loc = new Location(world, x, y, z);

        var ocean = loc.getBlock().getBiome().getKey().getKey().contains("ocean")
                || loc.getBlock().getBiome().getKey().getKey().contains("slopes")
                || loc.getBlock().getBiome().getKey().getKey().contains("river");

        if (ocean) {
            return generateRandomLocation();
        }

        loc.getChunk().load(true);

        assert world != null;
        y = world.getHighestBlockYAt(loc) + 2;
        loc.setY(y);

        return loc;
    }

    public static void acceptTPA(Player target) {
        if (target.getWorld().getName().equalsIgnoreCase("world_monster")) {
            tpa.remove(target);
            return;
        }
        if (tpa.containsKey(target)) {
            Player sender = tpa.get(target);
            sender.teleport(target);
            sender.sendMessage(getPrefix() + "§e" + target.getName() + " §7accept the request");
            target.sendMessage(getPrefix() + "§aYou have accept the request");
            tpa.remove(target);
        } else if (tpaHere.containsKey(target)) {
            acceptTPAHere(target);
        } else {
            target.sendMessage(getPrefix() + "§cYou have not a request!");
        }
    }

    public static void sendTPAHere(Player sender, Player target) {
        tpaHere.put(target, sender);
        sender.sendMessage(getPrefix() + "§7Die Anfrage wurde gesendet an §a" + target.getName() + "§7!");
        target.sendMessage(getPrefix() + "§7Du hast eine Anfrage erhalten von §c" + sender.getName() + "§7, das du dich zu ihm/ihr teleportierst!");
    }

    private static void acceptTPAHere(Player target) {
        if (tpaHere.containsKey(target)) {
            Player sender = tpaHere.get(target);
            target.teleport(sender);
            sender.sendMessage(getPrefix() + "§e" + target.getName() + " §7hat die Teleport Anfrage angemommen!");
            target.sendMessage(getPrefix() + "§aDu hast die Anfrage angenommen!");
            tpaHere.remove(target);
        } else {
            target.sendMessage(getPrefix() + "§cYou have not a request!");
        }
    }

    public static void denyTPA(Player target) {
        if (tpa.containsKey(target)) {
            Player sender = tpa.get(target);
            sender.sendMessage(getPrefix() + "§cDeine Teleport Anfrage wurde abgelehnt!");
            tpa.remove(target);
        } else {
            target.sendMessage(getPrefix() + "§cYou have not a request!");
        }
    }

    public static void sendTPA(Player sender, Player target) {
        tpa.put(target, sender);
        sender.sendMessage(getPrefix() + "§7Du hast eine Teleport Anfrage an §c" + target.getName() + " §7gesendet!");
        target.sendMessage(getPrefix() + "§7Du hast eine Teleport Anfrage erhalten von §c" + sender.getName() + "§7!");
    }

    private static Object getWebData(String u, String data) {
        try {
            URL url = new URL(u);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line = reader.readLine();
            JSONObject json = new JSONObject(line);
            return json.getString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPluginVersion() {
        return getWebData("http://playscorpion.de/honey_version.json", "plugin").toString();
    }

    public static boolean needUpdate(String version) {
        String current = getWebData("http://playscorpion.de/honey_version.json", "plugin").toString();
        return !current.equalsIgnoreCase(version);
    }

    public static boolean isSnapshot(String version) {
        return version.contains("SNAPSHOT");
    }

    public static boolean hideServerDetails() {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return file.getBoolean("hide-server-detail");
    }

    public static String getPrefix() {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return getColorCode(file.getString("prefix")) + " ";
    }

    public static String getMessage(String path) {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        var lang = file.getString("language").toLowerCase();
        FileManager currentLangData = new FileManager("plugins/HoneyCore/Lang/" + lang + ".yml");
        return currentLangData.getString(path).replace("%prefix%", file.getString("prefix"));
    }

    public static String getPermission(String permission) {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return file.getString("permission." + permission);
    }

    public static double getMaxMoney() {
        return new BigDecimal("100000000000000").doubleValue();
    }

    public static String renderValue(double v) {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(v);
    }

    public static String renderValueForSave(double v) {
        DecimalFormat format = new DecimalFormat("####.##");
        return format.format(v);
    }

    public static Location getSpawn() {
        FileManager file = new FileManager("plugins/HoneyCore/Spawn.yml");
        return file.getLocation("spawn");
    }

    public static void setSpawn(Location location) {
        FileManager file = new FileManager("plugins/HoneyCore/Spawn.yml");
        file.set("spawn", location);
        file.save();
    }

    public static void sendNoPermission(Player p) {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        var lang = file.getString("language").toLowerCase();
        FileManager currentLangData = new FileManager("plugins/HoneyCore/Lang/" + lang + ".yml");
        var m = currentLangData.getString("message.no-permission").replace("%prefix%", file.getString("prefix"));
        p.sendMessage(getColorCode(m));
    }

}
