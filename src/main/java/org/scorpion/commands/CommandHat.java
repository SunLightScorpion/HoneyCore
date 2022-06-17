package org.scorpion.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

import java.util.Objects;

public class CommandHat implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("hat"))) {
                if (args.length == 0) {

                    ItemStack item = p.getInventory().getItemInMainHand();
                    if (p.getInventory().getHelmet() != null) {
                        p.getInventory().setItemInMainHand(new ItemStack(Objects.requireNonNull(p.getInventory().getHelmet())));
                    } else {
                        p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    }
                    p.getInventory().setHelmet(new ItemStack(item));
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.hat")));
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }
}
