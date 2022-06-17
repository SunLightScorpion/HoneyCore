package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.user.HoneyUser;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 12/22/2021
 */
public class CommandBack implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            var user = new HoneyUser(p.getUniqueId());

            if(user.getDeathPoint() == null){
                p.teleport(HoneyAPI.getSpawn());
            } else {
                p.teleport(user.getDeathPoint());
            }
        }
        return false;
    }

}
