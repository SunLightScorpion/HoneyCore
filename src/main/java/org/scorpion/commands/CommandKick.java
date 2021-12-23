package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.util.Util;

/**
 * @author Lukas on 12/23/2021
 */
public class CommandKick implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            StringBuilder builder = new StringBuilder();
            if (!p.hasPermission("api.kick")) {
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
                    t.sendMessage("§cDu wurdest gekickt: §4" + builder);
                    t.kickPlayer("§c" + builder);
                } else {
                    p.sendMessage(Util.getPrefix() + "§4Spieler §7" + args[0] + " §4wurde nicht gefunden!");
                }
            } else if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    t.sendMessage("§cDu wurdest gekickt");
                    t.kickPlayer("§cDu wurdest gekickt");
                }
            } else {
                p.sendMessage(Util.getPrefix() + "§c/kick <Spieler> <Grund>");
            }
        }
        return false;
    }

}
