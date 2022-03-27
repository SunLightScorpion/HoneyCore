package org.scorpion.util.warp;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.scorpion.api.HoneyAPI;

import java.io.File;
import java.io.IOException;

/**
 * @author Lukas on 12/23/2021
 */
public class Warp {

    String warp;

    public Warp(String warp) {
        this.warp = warp;
    }

    public String getWarpName() {
        return warp;
    }

    public boolean existWarp() {
        File file = new File("plugins/HoneyCore/warp/" + getWarpName() + ".yml");
        return file.exists();
    }

    public void setWarp(Location location) {
        File file = new File("plugins/ServerAPI/warp/" + getWarpName() + ".yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        data.set("warp", location);
        try {
            data.save(file);
        } catch (IOException ignored) {
        }
        HoneyAPI.reloadWarps();
    }

    public void delWarp() {
        File file = new File("plugins/ServerAPI/warp/" + getWarpName() + ".yml");
        file.delete();
    }

    public void teleport(Player p) {
        File file = new File("plugins/ServerAPI/warp/" + getWarpName() + ".yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        Location loc = data.getLocation("warp");
        if (loc != null) {
            p.teleport(loc);
        }
    }

    public Location getLocation() {
        File file = new File("plugins/ServerAPI/warp/" + getWarpName() + ".yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        return data.getLocation("warp");
    }

}
