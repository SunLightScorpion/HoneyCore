package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

public class CommandKill implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("kill"))) {
                if (args.length == 0) {
                    p.setHealth(0);
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.kill")));
                }

                if (args.length == 1) {
                    if (p.hasPermission(HoneyAPI.getPermission("kill-target"))) {
                        Player t = Bukkit.getPlayer(args[0]);

                        if (t == null) {
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.player-not-found")));
                            return true;
                        }

                        t.setHealth(0);
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.kill-target")));
                    } else {
                        HoneyAPI.sendNoPermission(p);
                    }
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }
}
