package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

public class CommandSudo implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            StringBuilder builder = new StringBuilder();
            if (p.hasPermission(HoneyAPI.getPermission("sudo"))) {
                if (args.length == 2) {
                    Player t = Bukkit.getPlayer(args[0]);

                    StringBuilder msg = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        msg.append(args[i]).append(" ");
                    }
                    builder.append(msg);

                    if (t == null) {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.player-not-found")));
                        return true;
                    }
                    t.chat(builder.toString());
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.sudo")));
                } else {
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.sudo-syntax")));
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }
}
