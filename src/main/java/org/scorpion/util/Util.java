package org.scorpion.util;

import net.md_5.bungee.api.ChatColor;
import org.scorpion.util.file.FileManager;

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
        file.delete();

        if (!file.exist()) {
            file.set("language", "de");
            file.set("permission.gamemode", "honey.gamemode");
            file.set("permission.gamemode-target", "honey.gamemode.target");
            file.set("permission.heal", "honey.heal");
        }

        FileManager de = new FileManager("plugins/HoneyCore/Lang/de.yml");
        de.delete();

        if (!de.exist()) {
            de.set("message.gamemode", "&6Dein Spielmodus ist jetzt auf &c%gm%&6!");
            de.set("message.gamemode-target", "&6Der Spielmodus von &4%target% &6ist jetzt auf &c%gm%&6!");
            de.set("message.player-not-found", "&c%target% ist nicht online!");
        }

        FileManager en = new FileManager("plugins/HoneyCore/Lang/en.yml");
        en.delete();

        if (!en.exist()) {
            en.set("message.gamemode", "&6Your gamemode changed to &c%gm%&6!");
            en.set("message.gamemode", "&6The gamemode from &4%target% &6changed to &c%gm%&6!");
            en.set("message.player-not-found", "&c%target% is not online!");
        }

        file.save();
        de.save();
        en.save();
    }

    public static String getMessage(String path) {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        var lang = file.getString("language").toLowerCase();
        FileManager currentLangData = new FileManager("plugins/HoneyCore/Lang/" + lang + ".yml");
        return currentLangData.getString(path);
    }

    public static String getPermission(String permission) {
        FileManager file = new FileManager("plugins/HoneyCore/Settings.yml");
        return file.getString("permission." + permission);
    }

}
