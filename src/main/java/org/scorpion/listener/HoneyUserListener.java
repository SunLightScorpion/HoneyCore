package org.scorpion.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.user.User;

/**
 * @author Lukas on 11/24/2021
 */
public class HoneyUserListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User user = new HoneyUser(p.getUniqueId());
        user.createUser();
    }

}
