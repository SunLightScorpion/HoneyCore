package org.scorpion.api;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.Time;
import org.scorpion.util.file.FileManager;
import org.scorpion.util.user.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lukas on 11/15/2021
 */
public class HoneyAPI {

    public final static String VERSION = "1.7-SNAPSHOT";
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

    public static void addSetting(String data, Object value) {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");

        if (!file.isSet(data)) {
            file.set(data, value);
        }
        file.save();
    }

    public static void manageLang() {
        addSetting("prefix", "&8[&eHoney&6Core&8]");
        addSetting("hide-server-detail", true);
        addSetting("random-teleport-range", 4000);
        addSetting("welcome-new-player", true);
        addSetting("permission.gamemode", "honey.gamemode");
        addSetting("permission.gamemode-target", "honey.gamemode.target");
        addSetting("permission.heal", "honey.heal");
        addSetting("permission.heal-target", "honey.heal.target");
        addSetting("permission.setspawn", "honey.setspawn");
        addSetting("permission.admin", "honey.admin");
        addSetting("permission.tp", "honey.tp");
        addSetting("permission.enderchest-target", "honey.enderchest.target");
        addSetting("permission.fly", "honey.fly");
        addSetting("permission.setwarp", "honey.setwarp");
        addSetting("permission.sun", "honey.sun");
        addSetting("permission.kick", "honey.kick");
        addSetting("permission.rtp-bypass", "honey.rtp.bypass");
        addSetting("permission.glow", "honey.glow");
        addSetting("permission.glow-target", "honey.glow.target");
        addSetting("permission.seen-target", "honey.seen.target");
        addSetting("permission.ban-player", "honey.ban");
        addSetting("permission.kill", "honey.kill");
        addSetting("permission.kill-target", "honey.kill.target");
        addSetting("permission.broadcast", "honey.broadcast");
        addSetting("permission.sudo", "honey.sudo");
        addSetting("permission.god", "honey.god");
        addSetting("permission.god-target", "honey.god.target");
        addSetting("permission.hat", "honey.hat");
        addSetting("message.gamemode", "%prefix% &6Your gamemode changed to &c%gm%&6!");
        addSetting("message.gamemode-target", "%prefix% &6The gamemode from &4%target% &6changed to &c%gm%&6!");
        addSetting("message.player-not-found", "%prefix% &c%target% is not online!");
        addSetting("message.heal", "%prefix% &2You are now &ahealed&2!");
        addSetting("message.heal-target", "%prefix% &2You have healed &a%target%&2!");
        addSetting("message.spawn", "%prefix% &2Set spawn.");
        addSetting("message.spawn-set", "%prefix% &2Set spawn.");
        addSetting("message.spawn-teleport", "%prefix% &2You are teleported to spawn.");
        addSetting("message.no-permission", "%prefix% &cYou have not the permission to do that.");
        addSetting("message.spawn-not-set", "%prefix% &cSpawn is not set.");
        addSetting("message.welcome-new-player", "&d%player% is a new player!");
        addSetting("message.home-teleport", "%prefix% §aYou teleported to home §b%home%§a!");
        addSetting("message.home-not-exist", "%prefix% §4Home do not exist!");
        addSetting("message.home-delete", "%prefix% §cDelete home %home%");
        addSetting("message.home-username-error", "%prefix% §cYou do not allowed to use the username for a home name!");
        addSetting("message.home-set", "%prefix% §aHome set");
        addSetting("message.home-max", "%prefix% §cYou have reached the max size of homes!");
        addSetting("message.weather-sun", "%prefix% §aThe sun shines now!");
        addSetting("message.weather-rain", "%prefix% §cIt will rain now!");
        addSetting("message.time-night", "%prefix% §9The time was set to night!");
        addSetting("message.time-day", "%prefix% §aThe time was set to day!");
        addSetting("message.warp-not-exist", "%prefix% §cThe warp §e%warp% §cexists not!");
        addSetting("message.warp-delete", "%prefix% §7You have delete the warp §c%warp%§7!");
        addSetting("message.warp-set", "%prefix% §7You have set the warp §a%warp%§7!");
        addSetting("message.warp-teleport", "%prefix% §7You teleported to warp §6%warp%§7!");
        addSetting("message.world-not-found", "%prefix% §cWorld not found");
        addSetting("message.fly-state", "%prefix% §c§7Fly: §e%state%");
        addSetting("message.fly-state-target", "%prefix% §c§7Fly: §e%state% §8| §6%target%");
        addSetting("message.kick", "§cYou were kicked!");
        addSetting("message.kick-syntax", "%prefix% §c/kick <Player> <Reason>!");
        addSetting("message.glow", "%prefix% §7Glow: §e%state%");
        addSetting("message.glow-target", "%prefix% §7Glow: §e%state% §8| §6%target%");
        addSetting("message.rtp-deny", "%prefix% §cYou can't teleport because the cooldown hasn't worn off! (500 seconds total)");
        addSetting("message.seen-target-offline", "%prefix% §7The player §c%target% §7is offline since §e%time%");
        addSetting("message.seen-target-online", "%prefix% §7The player §c%target% §7is online since §e%time%");
        addSetting("message.ban-player", "§c§lYou was banned!\n§7Reason: §e%reason%\n§7End: §e%time%");
        addSetting("message.ban-syntax", "%prefix% §c/ban <Player> <Reason>");
        addSetting("message.ban-player-message", "%prefix% §e%target% §7wurde gebannt.");
        addSetting("message.kill", "%prefix% §7You was killed!");
        addSetting("message.kill-target", "%prefix% §7The player §e%target% §7was killed!");
        ;
        addSetting("message.broadcast", "%prefix% §8» §a%bc%");
        addSetting("message.broadcast-syntax", "%prefix% §c/Broadcast (Text)");
        addSetting("message.sudo-syntax", "%prefix% §c/Sudo (Player) (Message)");
        addSetting("message.sudo-target", "%prefix% §e%target% §7has completed his task");
        addSetting("message.god", "%prefix% §7God: §e%state%");
        addSetting("message.god-target", "%prefix% §7God: §e%state% §8| §6%target%");
        addSetting("message.hat", "%prefix% §7You put on a hat.");
        addSetting("join-message", "§8[§a+§8] §7%player%");
        addSetting("quit-message", "§8[§4-§8] §7%player%");
    }

    public static User getUser(UUID id) {
        return new HoneyUser(id);
    }

    public static int getRandomTeleportRange() {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return Integer.parseInt(file.getString("random-teleport-range"));
    }

    public static boolean welcomeNewPlayer() {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return file.getBoolean("welcome-new-player");
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
        if (!p.hasPermission(HoneyAPI.getPermission("rtp-bypass"))) {
            if (!canRTP(p.getUniqueId())) {
                p.sendMessage(getColorCode(getMessage("message.rtp-deny")));
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
        int x = random.nextInt(getRandomTeleportRange()) + 200;
        int y = 150;
        int z = random.nextInt(getRandomTeleportRange()) + 200;
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
            sender.sendMessage(getPrefix() + "§e" + target.getName() + " §7has accepted the teleport request!");
            target.sendMessage(getPrefix() + "§aYou have accepted the request!");
            tpaHere.remove(target);
        } else {
            target.sendMessage(getPrefix() + "§cYou have not a request!");
        }
    }

    public static void denyTPA(Player target) {
        if (tpa.containsKey(target)) {
            Player sender = tpa.get(target);
            sender.sendMessage(getPrefix() + "§cYour teleport request was rejected!");
            tpa.remove(target);
        } else {
            target.sendMessage(getPrefix() + "§cYou have not a request!");
        }
    }

    public static void sendTPA(Player sender, Player target) {
        tpa.put(target, sender);
        sender.sendMessage(getPrefix() + "§7You have sent a teleport request to §c" + target.getName() + " §7!");
        target.sendMessage(getPrefix() + "§7You have received a teleport request from §c" + sender.getName() + "§7!");
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
        return getWebData("https://sunlightscorpion.de/honey.json", "plugin").toString();
    }

    public static boolean needUpdate(String version) {
        String current = getWebData("https://sunlightscorpion.de/honey.json", "plugin").toString();
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
        return file.getString(path).replace("%prefix%", file.getString("prefix"));
    }

    public static String getMessage(String path, String player) {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return file.getString(path).replace("%prefix%", file.getString("prefix")).replace("%player%", player);
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

        var loc = file.getLocation("spawn");

        if (loc == null) {
            return Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
        }

        return loc;
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

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    public static String getCurrentDate(long current) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(current);
        return sdf.format(date);
    }
}
