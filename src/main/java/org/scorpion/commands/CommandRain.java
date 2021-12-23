package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.util.Util;

public class CommandRain implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (!p.hasPermission("core.rain")) {
                return true;
            }
            p.getWorld().setStorm(true);
            p.sendMessage(Util.getPrefix() + " §cEs erscheint Regen!");
        }
        return false;
    }

}
