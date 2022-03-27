package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 11/24/2021
 */
public class CommandSetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("setspawn"))) {
                p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.spawn-set")));
                HoneyAPI.setSpawn(p.getLocation());
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }

}
