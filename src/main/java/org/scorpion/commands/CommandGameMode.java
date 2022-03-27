package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 11/15/2021
 */
public class CommandGameMode implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                String data = args[0];
                if (p.hasPermission(HoneyAPI.getPermission("gamemode"))) {
                    switch (data) {
                        case "0", "survival" -> {
                            p.setGameMode(GameMode.SURVIVAL);
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                        }
                        case "1", "creative" -> {
                            p.setGameMode(GameMode.CREATIVE);
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                        }
                        case "2", "adventure" -> {
                            p.setGameMode(GameMode.ADVENTURE);
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                        }
                        case "3", "spectator" -> {
                            p.setGameMode(GameMode.SPECTATOR);
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                        }
                    }
                } else {
                    HoneyAPI.sendNoPermission(p);
                }
            } else if (args.length == 2) {
                String data = args[0];
                Player t = Bukkit.getPlayer(args[1]);

                if (t == null) {
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.player-not-found").replace("%target%", args[1])));
                    return true;
                }

                if (p.hasPermission(HoneyAPI.getPermission("gamemode-target"))) {
                    switch (data) {
                        case "0", "survival" -> {
                            t.setGameMode(GameMode.SURVIVAL);
                            t.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode-target").replace("%gm%", t.getGameMode().toString()).replace("%target%", t.getName())));
                        }
                        case "1", "creative" -> {
                            t.setGameMode(GameMode.CREATIVE);
                            t.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode-target").replace("%gm%", t.getGameMode().toString()).replace("%target%", t.getName())));
                        }
                        case "2", "adventure" -> {
                            t.setGameMode(GameMode.ADVENTURE);
                            t.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode-target").replace("%gm%", t.getGameMode().toString()).replace("%target%", t.getName())));
                        }
                        case "3", "spectator" -> {
                            t.setGameMode(GameMode.SPECTATOR);
                            t.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode").replace("%gm%", p.getGameMode().toString())));
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.gamemode-target").replace("%gm%", t.getGameMode().toString()).replace("%target%", t.getName())));
                        }
                    }
                } else {
                    HoneyAPI.sendNoPermission(p);
                }
            }
        }
        return false;
    }

}
