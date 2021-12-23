package org.scorpion.user;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.scorpion.util.Util;
import org.scorpion.util.file.FileManager;
import org.scorpion.util.item.ItemBuilder;
import org.scorpion.util.user.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Lukas on 11/24/2021
 */
public record HoneyUser(UUID uuid) implements User {

    @Override
    public boolean existPlayer() {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        return file.exist();
    }

    @Override
    public void createUser() {
        if (!existPlayer()) {
            FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
            file.set("money", "100");
            file.save();

            var off = Bukkit.getOfflinePlayer(uuid);

            if (off.getPlayer() != null) {
                var player = off.getPlayer();
                ItemStack[] kit = new ItemStack[]{
                        new ItemBuilder(Material.STONE_SWORD).build(),
                        new ItemBuilder(Material.STONE_PICKAXE).build(),
                        new ItemBuilder(Material.STONE_AXE).build(),
                        new ItemBuilder(Material.STONE_SHOVEL).build(),
                        new ItemBuilder(Material.STONE_HOE).build(),
                        new ItemBuilder(Material.COOKED_BEEF).setAmount(16).build(),
                        new ItemBuilder(Material.GOLDEN_SHOVEL).build(),
                };

                player.teleport(Util.getSpawn());
                player.getInventory().addItem(kit);
            }
        }
    }

    @Override
    public int getMaxHomes() {
        return 5;
    }

    @Override
    public List<String> getHomes() {
        File file = new File("plugins/HoneyCore/playerdata/" + uuid.toString() + ".yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        return data.getStringList("homes");
    }

    @Override
    public Location getHome(String name) {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        return file.getLocation("home-" + name);
    }

    @Override
    public void addHome(String home) {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        List<String> list = getHomes();
        if (!list.contains(home)) {
            list.add(home);
        }
        file.set("homes", list);
        file.save();
    }

    @Override
    public void removeHome(String home) {
        File file = new File("plugins/HoneyCore/playerdata/" + uuid.toString() + ".yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        List<String> list = getHomes();
        list.remove(home);
        data.set("homes", list);
        try {
            data.save(file);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void saveLocation(String name, Location location) {
        File file = new File("plugins/HoneyCore/playerdata/" + uuid.toString() + ".yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        data.set("home-" + name, location);
        try {
            data.save(file);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void removeLocation(String name) {
        File file = new File("plugins/HoneyCore/playerdata/" + uuid.toString() + ".yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        data.set("home-" + name, null);
        try {
            data.save(file);
        } catch (IOException ignored) {
        }
    }

    @Override
    public String listHomes() {

        if (getHomes().size() == 0) {
            return "§cHomes not found";
        }

        StringBuilder builder = new StringBuilder();
        for (String h : getHomes()) {
            builder.append("§a").append(h).append("§c, ");
        }
        builder.setLength(builder.length() - 3);
        return builder.toString();
    }

    @Override
    public boolean existHome(String name) {
        return getHomes().contains(name);
    }

    @Override
    public void setDeathPoint(Location location) {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        file.set("death-point", location);
        file.save();
    }

    @Override
    public Location getDeathPoint() {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        return file.getLocation("death-point");
    }

}
