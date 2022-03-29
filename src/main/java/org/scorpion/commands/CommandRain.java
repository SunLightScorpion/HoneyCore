package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.api.HoneyAPI;

public class CommandRain implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (!p.hasPermission("core.rain")) {
                return true;
            }
            p.getWorld().setStorm(true);
            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.weather-rain")));
        }
        return false;
    }

}
