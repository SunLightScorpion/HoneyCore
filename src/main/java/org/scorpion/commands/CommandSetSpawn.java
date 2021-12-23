package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.util.Util;

/**
 * @author Lukas on 11/24/2021
 */
public class CommandSetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(Util.getPermission("setspawn"))) {
                p.sendMessage(Util.getColorCode(Util.getMessage("message.spawn-set")));
                Util.setSpawn(p.getLocation());
            } else {
                Util.sendNoPermission(p);
            }
        }
        return false;
    }

}
