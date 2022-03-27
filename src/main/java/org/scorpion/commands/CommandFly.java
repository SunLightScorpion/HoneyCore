package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 12/23/2021
 */
public class CommandFly implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("fly"))) {
                if (!p.getAllowFlight()) {
                    p.setAllowFlight(true);
                } else {
                    p.setFlying(false);
                    p.setAllowFlight(false);
                }
            }
        }
        return false;
    }

}
