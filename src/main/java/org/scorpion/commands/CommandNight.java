package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.api.HoneyAPI;

public class CommandNight implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (!p.hasPermission(HoneyAPI.getPermission("night"))) {
                return true;
            }
            p.getWorld().setTime(18000);
            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.time-night")));
        }
        return false;
    }

}
