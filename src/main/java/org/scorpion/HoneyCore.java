package org.scorpion;

import org.bukkit.plugin.java.JavaPlugin;
import org.scorpion.commands.*;
import org.scorpion.listener.HoneyCommandListener;
import org.scorpion.listener.HoneyUserListener;
import org.scorpion.api.HoneyAPI;

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
        Objects.requireNonNull(getCommand("fly")).setExecutor(new CommandFly());
        Objects.requireNonNull(getCommand("ping")).setExecutor(new CommandPing());

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

        Objects.requireNonNull(getCommand("warp")).setExecutor(new CommandWarp());
        Objects.requireNonNull(getCommand("warp")).setTabCompleter(new CommandWarp());
        Objects.requireNonNull(getCommand("setwarp")).setExecutor(new CommandSetWarp());
        Objects.requireNonNull(getCommand("delwarp")).setExecutor(new CommandDelwarp());

        Objects.requireNonNull(getCommand("ec")).setExecutor(new CommandEnderChest());
        Objects.requireNonNull(getCommand("enderchest")).setExecutor(new CommandEnderChest());
        Objects.requireNonNull(getCommand("ui")).setExecutor(new CommandUI());

        Objects.requireNonNull(getCommand("sun")).setExecutor(new CommandSun());
        Objects.requireNonNull(getCommand("rain")).setExecutor(new CommandRain());
        Objects.requireNonNull(getCommand("day")).setExecutor(new CommandDay());
        Objects.requireNonNull(getCommand("night")).setExecutor(new CommandNight());
        Objects.requireNonNull(getCommand("glow")).setExecutor(new CommandGlow());
        Objects.requireNonNull(getCommand("seen")).setExecutor(new CommandSeen());
        Objects.requireNonNull(getCommand("ban")).setExecutor(new CommandBan());
        Objects.requireNonNull(getCommand("kill")).setExecutor(new CommandKill());
        Objects.requireNonNull(getCommand("broadcast")).setExecutor(new CommandBroadcast());
        Objects.requireNonNull(getCommand("sudo")).setExecutor(new CommandSudo());

        getServer().getPluginManager().registerEvents(new HoneyUserListener(), this);
        getServer().getPluginManager().registerEvents(new HoneyCommandListener(), this);

        HoneyAPI.manageLang();
        HoneyAPI.initWarp();
    }

}
