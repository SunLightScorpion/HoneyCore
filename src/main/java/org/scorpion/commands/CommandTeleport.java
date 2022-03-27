package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

import java.util.Random;

/**
 * @author Lukas on 12/19/2021
 */
public class CommandTeleport implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("tp"))) {
                if (args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t != null) {
                        p.teleport(t);
                        p.sendMessage(HoneyAPI.getPrefix() + "§7Teleport to §e" + t.getName() + " §7!");
                    }
                } else if (args.length == 2) {
                    Player t = Bukkit.getPlayer(args[0]);
                    Player t2 = Bukkit.getPlayer(args[1]);
                    if (t == null) {
                        return false;
                    }
                    if (t2 == null) {
                        return false;
                    }
                    t.teleport(t2);
                    p.sendMessage("§c" + t.getName() + " §7to §c" + t2.getName() + " §7!");
                } else if (args.length == 3) {
                    try {
                        double x = Double.parseDouble(args[0]);
                        double y = Double.parseDouble(args[1]);
                        double z = Double.parseDouble(args[2]);
                        Location loc = new Location(p.getWorld(), x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch());
                        p.teleport(loc);
                        p.sendMessage(HoneyAPI.getPrefix() + "§7Teleported to §c" + x + "§7, §c" + y + "§7, §c" + z + " §7!");
                    } catch (NumberFormatException ignored) {
                    }
                } else if (args.length == 4) {
                    try {
                        double x = Double.parseDouble(args[0]);
                        double y = Double.parseDouble(args[1]);
                        double z = Double.parseDouble(args[2]);
                        String world = args[3];
                        if (Bukkit.getWorld(world) == null) {
                            p.sendMessage(HoneyAPI.getPrefix() + "§4The world can't fount");
                            return false;
                        }
                        Location loc = new Location(Bukkit.getWorld(world), x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch());
                        p.teleport(loc);
                        p.sendMessage(HoneyAPI.getPrefix() + "§7You have teleported to §c" + x + "§7, §c" + y + "§7, §c" + z + "§7, §c" + world + " §7!");
                    } catch (NumberFormatException ignored) {
                    }
                } else if (args.length == 0) {
                    Player random = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                    p.teleport(random);
                    p.sendMessage(HoneyAPI.getPrefix() + "§7You teleported to §c" + random.getName() + " §7!");
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }

}
