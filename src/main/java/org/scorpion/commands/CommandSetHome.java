package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.Util;
import org.scorpion.util.command.ScorpionCommand;

public class CommandSetHome extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                var data = args[0];
                var user = new HoneyUser(p.getUniqueId());

                if (data.equalsIgnoreCase(p.getName())) {
                    p.sendMessage(Util.getPrefix() + "§cYou do not allowed to use the username for a home name!");
                    return false;
                }

                if (user.getHomes().size() >= user.getMaxHomes()) {
                    if (user.getHomes().contains(data)) {
                        user.saveLocation(data, p.getLocation());
                        p.sendMessage(Util.getPrefix() + "§aHome set");
                        return false;
                    }
                    p.sendMessage(Util.getPrefix() + "§cYou have reached the max size of homes!");
                    return false;
                }
                user.addHome(data);
                user.saveLocation(data, p.getLocation());
                p.sendMessage(Util.getPrefix() + "§aHome set!");
            } else {
                p.sendMessage(Util.getPrefix() + "§c/sethome <NAME>");
            }
        }
        return false;
    }

}
