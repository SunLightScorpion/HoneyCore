package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.util.Util;

public class CommandSun implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (!p.hasPermission("core.sun")) {
                return true;
            }
            p.getWorld().setThundering(false);
            p.getWorld().setStorm(false);
            p.sendMessage(Util.getPrefix() + " §aEs scheint nun die schöne Sonne!");
        }
        return false;
    }

}
