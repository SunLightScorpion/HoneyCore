package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 3/27/2022
 */
public class CommandUI implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            p.openInventory(HoneyAPI.getUser(p.getUniqueId()).getUserInterface().getUserInterface(p));
        }
        return false;
    }
}
