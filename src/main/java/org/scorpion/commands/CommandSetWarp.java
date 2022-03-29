package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.util.command.ScorpionCommand;
import org.scorpion.util.warp.Warp;

public class CommandSetWarp extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("setwarp"))) {
                if (args.length == 1) {
                    String data = args[0];
                    Warp warp = new Warp(data);
                    warp.setWarp(p.getLocation());
                    p.sendMessage(HoneyAPI.getMessage(HoneyAPI.getMessage("message.warp-set").replace("%warp%", warp.getWarpName())));
                }
            }
        }
        return false;
    }

}
