package org.scorpion.commands;

import org.bukkit.Bukkit;
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
            if (args.length == 0) {
                if (p.hasPermission(Util.getPermission("heal"))) {
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.setSaturation(20);
                    p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                    p.sendMessage(Util.getColorCode(Util.getMessage("message.heal")));
                } else {
                    Util.sendNoPermission(p);
                }
            } else if (args.length == 1) {
                if (p.hasPermission(Util.getPermission("heal-target"))) {

                    Player t = Bukkit.getPlayer(args[0]);

                    if (t == null) {
                        p.sendMessage(Util.getColorCode(Util.getMessage("message.player-not-found").replace("%target%", args[0])));
                        return true;
                    }

                    t.setHealth(20);
                    t.setFoodLevel(20);
                    t.setSaturation(20);
                    t.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                    t.sendMessage(Util.getColorCode(Util.getMessage("message.heal")));
                    p.sendMessage(Util.getColorCode(Util.getMessage("message.heal-target").replace("%target%", t.getName())));
                } else {
                    Util.sendNoPermission(p);
                }
            }
        }
        return false;
    }

}
