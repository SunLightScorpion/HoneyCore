package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

public class CommandBroadcast implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            StringBuilder builder = new StringBuilder();
            if (p.hasPermission(HoneyAPI.getPermission("broadcast"))) {
                if (args.length > 1) {
                    StringBuilder msg = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        msg.append(args[i]).append(" ");
                    }
                    builder.append(msg);

                    Bukkit.broadcastMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.broadcast")));
                } else {
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.broadcast-syntax")));
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }
}
