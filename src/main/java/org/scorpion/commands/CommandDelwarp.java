package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.HoneyCore;
import org.scorpion.api.HoneyAPI;
import org.scorpion.util.command.ScorpionCommand;
import org.scorpion.util.warp.Warp;

public class CommandDelwarp extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission("api.delwarp")) {
                if (args.length == 1) {
                    String data = args[0];
                    Warp warp = new Warp(data);
                    if (warp.existWarp()) {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.warp-delete").replace("%warp%", warp.getWarpName())));
                        warp.delWarp();
                        Bukkit.getScheduler().runTaskLater(HoneyCore.getPlugin(), HoneyAPI::reloadWarps, 9);
                    } else {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.warp-not-exist").replace("%warp%", args[0])));
                    }
                }
            }
        }
        return false;
    }

}
