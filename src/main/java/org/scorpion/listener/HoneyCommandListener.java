package org.scorpion.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 11/24/2021
 */
public class HoneyCommandListener implements Listener {

    @EventHandler
    public void on(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage().split(" ")[0];

        if (HoneyAPI.hideServerDetails()) {
            if (cmd.equalsIgnoreCase("/bukkit:") ||
                    cmd.equalsIgnoreCase("/minecraft:") ||
                    cmd.equalsIgnoreCase("/version") ||
                    cmd.equalsIgnoreCase("/icanhasbukkit") ||
                    cmd.equalsIgnoreCase("/?") ||
                    cmd.equalsIgnoreCase("/about") ||
                    cmd.equalsIgnoreCase("/pl") ||
                    cmd.equalsIgnoreCase("/me") ||
                    cmd.equalsIgnoreCase("/help") ||
                    cmd.equalsIgnoreCase("/ver") ||
                    cmd.equalsIgnoreCase("/bungee")) {
                if (!p.hasPermission(HoneyAPI.getPermission("admin"))) {
                    HoneyAPI.sendNoPermission(p);
                    e.setCancelled(true);
                }
            }
        }
    }

}
