package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 12/22/2021
 */
public class CommandEnderChest implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.openInventory(p.getEnderChest());
            } else {
                if (p.hasPermission(HoneyAPI.getPermission("enderchest-target"))) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t != null) {
                        p.openInventory(t.getEnderChest());
                    } else {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.player-not-found").replace("%target%", args[1])));
                    }
                }
            }
        }
        return false;
    }

}
