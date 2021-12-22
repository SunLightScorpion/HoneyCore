package org.scorpion;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.scorpion.commands.*;
import org.scorpion.listener.HoneyCommandListener;
import org.scorpion.listener.HoneyUserListener;
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
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new CommandSetSpawn());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new CommandSpawn());
        Objects.requireNonNull(getCommand("tp")).setExecutor(new CommandTeleport());
        Objects.requireNonNull(getCommand("teleport")).setExecutor(new CommandTeleport());
        Objects.requireNonNull(getCommand("rtp")).setExecutor(new CommandRandomTeleport());

        Objects.requireNonNull(getCommand("tpa")).setExecutor(new CommandTPA());
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new CommandTPAccept());
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(new CommandTPDeny());
        Objects.requireNonNull(getCommand("tpahere")).setExecutor(new CommandTPAHere());

        Objects.requireNonNull(getCommand("workbench")).setExecutor(new CommandWorkbench());
        Objects.requireNonNull(getCommand("wb")).setExecutor(new CommandWorkbench());
        Objects.requireNonNull(getCommand("craft")).setExecutor(new CommandWorkbench());

        Objects.requireNonNull(getCommand("sethome")).setExecutor(new CommandSetHome());
        Objects.requireNonNull(getCommand("delhome")).setExecutor(new CommandDeleteHome());
        Objects.requireNonNull(getCommand("home")).setExecutor(new CommandHome());
        Objects.requireNonNull(getCommand("back")).setExecutor(new CommandBack());

        Objects.requireNonNull(getCommand("ec")).setExecutor(new CommandEnderChest());
        Objects.requireNonNull(getCommand("enderchest")).setExecutor(new CommandEnderChest());

        getServer().getPluginManager().registerEvents(new HoneyUserListener(), this);
        getServer().getPluginManager().registerEvents(new HoneyCommandListener(), this);

        Util.manageLang();
    }

}
