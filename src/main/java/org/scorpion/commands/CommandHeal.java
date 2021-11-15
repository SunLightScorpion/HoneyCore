package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorpion.util.Util;

/**
 * @author Lukas on 11/15/2021
 */
public class CommandHeal implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(Util.getPermission("heal"))) {
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setSaturation(20);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            }
        }
        return false;
    }

}
