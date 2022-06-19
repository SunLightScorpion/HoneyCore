package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.util.command.ScorpionCommand;

public class CommandTPDeny extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            HoneyAPI.denyTPA(p);
        }
        return false;
    }

}
