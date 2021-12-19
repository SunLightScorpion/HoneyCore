package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.util.Util;

import java.util.Random;

/**
 * @author Lukas on 12/19/2021
 */
public class CommandTeleport implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(Util.getPermission("tp"))) {
                if (args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t != null) {
                        p.teleport(t);
                        p.sendMessage(Util.getPrefix() + "§7Du hast dich zu §e" + t.getName() + " §7teleportiert!");
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
                    p.sendMessage("§7Du hast §c" + t.getName() + " §7zu §c" + t2.getName() + " §7teleportiert!");
                } else if (args.length == 3) {
                    try {
                        double x = Double.parseDouble(args[0]);
                        double y = Double.parseDouble(args[1]);
                        double z = Double.parseDouble(args[2]);
                        Location loc = new Location(p.getWorld(), x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch());
                        p.teleport(loc);
                        p.sendMessage(Util.getPrefix() + "§7Du hast dich zu §c" + x + "§7, §c" + y + "§7, §c" + z + " §7teleportiert!");
                    } catch (NumberFormatException ignored) {
                    }
                } else if (args.length == 4) {
                    try {
                        double x = Double.parseDouble(args[0]);
                        double y = Double.parseDouble(args[1]);
                        double z = Double.parseDouble(args[2]);
                        String world = args[3];
                        if (Bukkit.getWorld(world) == null) {
                            p.sendMessage(Util.getPrefix() + "§4Die Welt konnte nicht gefunden werden!");
                            return false;
                        }
                        Location loc = new Location(Bukkit.getWorld(world), x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch());
                        p.teleport(loc);
                        p.sendMessage(Util.getPrefix() + "§7Du hast dich zu §c" + x + "§7, §c" + y + "§7, §c" + z + "§7, §c" + world + " §7teleportiert!");
                    } catch (NumberFormatException ignored) {
                    }
                } else if (args.length == 0) {
                    Player random = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                    p.teleport(random);
                    p.sendMessage(Util.getPrefix() + "§7Du hast dich zu §c" + random.getName() + " §7teleportiert!");
                }
            } else {
                Util.sendNoPermission(p);
            }
        }
        return false;
    }

}
