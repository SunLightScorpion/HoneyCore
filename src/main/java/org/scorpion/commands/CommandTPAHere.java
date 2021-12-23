package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.util.Util;
import org.scorpion.util.command.ScorpionCommand;

public class CommandTPAHere extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);

                if (t != null) {
                    Util.sendTPAHere(p, t);
                } else {
                    p.sendMessage(Util.getColorCode(Util.getMessage("message.player-not-found").replace("%target%", args[0])));
                }
            }
        }
        return false;
    }

}
