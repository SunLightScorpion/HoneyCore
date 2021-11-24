package org.scorpion.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.scorpion.util.file.FileManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lukas on 11/15/2021
 */
public class Util {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

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

    public static boolean hideServerDetails() {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return file.getBoolean("hide-server-detail");
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
