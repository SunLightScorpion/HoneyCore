package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.command.ScorpionCommand;

public class CommandDeleteHome extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                var user = new HoneyUser(p.getUniqueId());
                var data = args[0];

                if (!user.existHome(data)) {
                    p.sendMessage(HoneyAPI.getPrefix() + "§cHome don't exist!");
                    return false;
                }

                user.removeLocation(data);
                user.removeHome(data);
                p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.home-delete").replace("%home%", data)));
            }
        }
        return false;
    }

}
