package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.Util;
import org.scorpion.util.command.ScorpionCommand;

public class CommandDeleteHome extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                var user = new HoneyUser(p.getUniqueId());
                var data = args[0];

                if (!user.existHome(data)) {
                    p.sendMessage(Util.getPrefix() + "§4Home do not exist");
                    return false;
                }

                user.removeLocation(data);
                user.removeHome(data);
                p.sendMessage(Util.getPrefix() + "§cDelete home " + data);
            }
        }
        return false;
    }

}
