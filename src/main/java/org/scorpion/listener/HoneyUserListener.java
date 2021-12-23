package org.scorpion.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.scorpion.HoneyCore;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.Util;
import org.scorpion.util.user.User;

/**
 * @author Lukas on 11/24/2021
 */
public class HoneyUserListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User user = new HoneyUser(p.getUniqueId());
        e.setJoinMessage("§8[§a+§7] §7"+p.getName());

        Bukkit.getScheduler().runTaskLater(HoneyCore.getPlugin(), user::createUser, 5);

        Bukkit.getScheduler().runTaskLater(HoneyCore.getPlugin(), () -> {
            if (p.hasPermission("honey.notify")) {
                if (Util.needUpdate(Util.VERSION) && !Util.isSnapshot(Util.VERSION)) {
                    p.sendMessage(Util.getPrefix() + "§cUpdate: §6https://www.spigotmc.org/resources/honeycore-coresystem.97646/");
                } else if (Util.needUpdate(Util.VERSION) && Util.isSnapshot(Util.VERSION)) {
                    p.sendMessage(Util.getPrefix() + "§4SNAPSHOT VERSION! (" + Util.VERSION + ")");
                    p.sendMessage(Util.getPrefix() + "§7Current version: §a" + Util.getPluginVersion());
                    p.sendMessage(Util.getPrefix() + "§cUpdate: §6https://www.spigotmc.org/resources/honeycore-coresystem.97646/");
                } else {
                    p.sendMessage(Util.getPrefix() + "§aNo updates found");
                }
            }
        }, 20 * 3);
    }

    @EventHandler
    public void on(PlayerDeathEvent e){
        Player p = e.getEntity();
        var user = new HoneyUser(p.getUniqueId());
        user.setDeathPoint(p.getLocation());
    }

    @EventHandler
    public void on(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(HoneyCore.getPlugin(), () -> {
            p.teleport(Util.getSpawn());
        }, 5);
    }

    @EventHandler
    public void on(PlayerQuitEvent e){
        Player p = e.getPlayer();
        e.setQuitMessage("§8[§4-§7] §7"+p.getName());
    }

}
