package org.scorpion.user.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.scorpion.api.HoneyAPI;
import org.scorpion.util.item.ItemBuilder;

/**
 * @author Lukas on 3/27/2022
 */
public class UserInterface {

    public final String INV_STRING = "§a/ui - User Info";

    public Inventory getUserInterface(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9 * 3, INV_STRING);

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build());
        }

        for (int j = 0; j < HoneyAPI.getUser(p.getUniqueId()).getMaxHomes(); j++) {
            inv.setItem(1 + j, new ItemBuilder(Material.WHITE_BED).setName("§c-").build());
        }

        if (HoneyAPI.getUser(p.getUniqueId()).getHomes().size() > 0) {
            for (int h = 0; h < HoneyAPI.getUser(p.getUniqueId()).getHomes().size(); h++) {
                inv.setItem(1 + h, new ItemBuilder(Material.LIME_BED).setName("§a" + HoneyAPI.getUser(p.getUniqueId()).getHomes().get(h)).build());
            }
        }

        inv.setItem(26, new ItemBuilder(Material.CRAFTING_TABLE).setName("§aCrafting").build());
        inv.setItem(25, new ItemBuilder(Material.ENDER_CHEST).setName("§5Enderchest").build());

        return inv;
    }

}
