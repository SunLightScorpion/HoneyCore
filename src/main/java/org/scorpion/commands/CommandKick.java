package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 12/23/2021
 */
public class CommandKick implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            StringBuilder builder = new StringBuilder();
            if (!p.hasPermission(HoneyAPI.getPermission("kick"))) {
                return false;
            }
            if (args.length > 1) {
                StringBuilder msg = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    msg.append(args[i]).append(" ");
                }
                builder.append(msg);
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    builder.setLength(msg.length() - 1);
                    t.kickPlayer("§c" + builder);
                } else {
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.player-not-found").replace("%target%", args[0])));
                }
            } else if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    t.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.kick")));
                    t.kickPlayer(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.kick")));
                }
            } else {
                p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.kick-syntax")));
            }
        }
        return false;
    }

}
