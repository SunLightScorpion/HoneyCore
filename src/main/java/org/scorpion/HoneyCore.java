package org.scorpion;

import org.bukkit.plugin.java.JavaPlugin;
import org.scorpion.commands.CommandGameMode;
import org.scorpion.commands.CommandHeal;
import org.scorpion.util.Util;

import java.util.Objects;

/**
 * @author Lukas on 11/15/2021
 */
public class HoneyCore extends JavaPlugin {

    private static HoneyCore plugin;

    public static HoneyCore getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new CommandGameMode());
        Objects.requireNonNull(getCommand("gm")).setExecutor(new CommandGameMode());
        Objects.requireNonNull(getCommand("heal")).setExecutor(new CommandHeal());

        Util.manageLang();
    }

}
