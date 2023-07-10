package org.scorpion.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.scorpion.HoneyCore;
import org.scorpion.api.HoneyAPI;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.user.User;

import java.util.Objects;

/**
 * @author Lukas on 11/24/2021
 */
public class HoneyUserListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User user = new HoneyUser(p.getUniqueId());
        e.setJoinMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("join-message", p.getName())));

        Bukkit.getScheduler().runTaskLater(HoneyCore.getPlugin(), user::createUser, 5);

        Bukkit.getScheduler().runTaskLater(HoneyCore.getPlugin(), () -> {
            if (p.hasPermission(HoneyAPI.getPermission("notify"))) {
                if (HoneyAPI.needUpdate(HoneyAPI.VERSION) && !HoneyAPI.isSnapshot(HoneyAPI.VERSION)) {
                    p.sendMessage(HoneyAPI.getPrefix() + "§cUpdate is available!");
                } else if (HoneyAPI.needUpdate(HoneyAPI.VERSION) && HoneyAPI.isSnapshot(HoneyAPI.VERSION)) {
                    p.sendMessage(HoneyAPI.getPrefix() + "§4SNAPSHOT VERSION! (" + HoneyAPI.VERSION + ")");
                    p.sendMessage(HoneyAPI.getPrefix() + "§7Current version: §a" + HoneyAPI.getPluginVersion());
                } else {
                    p.sendMessage(HoneyAPI.getPrefix() + "§aNo updates found");
                }
            }
        }, 20 * 3);

        user.setOnlineTime();
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getClickedInventory() == null) {
            return;
        }

        var view = e.getView();

        if (view.getTitle().equalsIgnoreCase("§a/ui - User Info")) {
            e.setCancelled(true);

            ItemStack item = e.getCurrentItem();
            Player p = (Player) e.getWhoClicked();

            if (item.getType() == Material.LIME_BED) {
                var display = Objects.requireNonNull(item.getItemMeta()).getDisplayName().replace("§a", "");

                p.teleport(HoneyAPI.getUser(p.getUniqueId()).getHome(display));
                p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.home-teleport").replace("%home%", display)));
            }
            if (item.getType() == Material.ENDER_CHEST) {
                p.openInventory(p.getEnderChest());
            }
            if (item.getType() == Material.CRAFTING_TABLE) {
                p.openWorkbench(null, true);
            }
        }

    }

    @EventHandler
    public void on(PlayerDeathEvent e) {
        Player p = e.getEntity();
        var user = new HoneyUser(p.getUniqueId());
        user.setDeathPoint(p.getLocation());
    }

    @EventHandler
    public void on(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(HoneyCore.getPlugin(), () -> {
            p.teleport(HoneyAPI.getSpawn());
        }, 5);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        HoneyUser user = new HoneyUser(p.getUniqueId());
        e.setQuitMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("quit-message", p.getName())));
        user.setOnlineTime();
    }

    @EventHandler
    public void on(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        HoneyUser user = new HoneyUser(p.getUniqueId());
        if (user.isBanned()) {
            if (user.getTime() == -1) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, HoneyAPI.getColorCode(HoneyAPI.getMessage("message.ban-player").replace("%reason%", user.getReason()).replace("%time%", "§cPermanent")));
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, HoneyAPI.getColorCode(HoneyAPI.getMessage("message.ban-player").replace("%reason%", user.getReason()).replace("%time%", HoneyAPI.getCurrentDate(user.getTime()))));
            }
        }
    }

    @EventHandler
    public void on(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player p) {
            HoneyUser user = new HoneyUser(p.getUniqueId());
            if (user.getGod().equalsIgnoreCase("true")) {
                e.setCancelled(true);
            }
        }
    }
}
