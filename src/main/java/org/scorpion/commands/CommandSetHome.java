package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.util.command.ScorpionCommand;

public class CommandSetHome extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                var data = args[0];
                var user = HoneyAPI.getUser(p.getUniqueId());

                if (data.equalsIgnoreCase(p.getName())) {
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.home-username-error")));
                    return false;
                }

                if (user.getHomes().size() >= user.getMaxHomes()) {
                    if (user.getHomes().contains(data)) {
                        user.saveLocation(data, p.getLocation());
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.home-set")));
                        return false;
                    }
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.home-max")));
                    return false;
                }
                user.addHome(data);
                user.saveLocation(data, p.getLocation());
                p.sendMessage(HoneyAPI.getPrefix() + "§aHome set!");
            } else {
                p.sendMessage(HoneyAPI.getPrefix() + "§c/sethome <NAME>");
            }
        }
        return false;
    }

}
