package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.command.ScorpionCommand;

public class CommandHome extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.openInventory(HoneyAPI.getUser(p.getUniqueId()).getUserInterface().getUserInterface(p));
            } else if (args.length == 1) {
                String home = args[0];
                if (new HoneyUser(p.getUniqueId()).getHomes().size() > 0) {
                    for (String list : new HoneyUser(p.getUniqueId()).getHomes()) {
                        if (list.equalsIgnoreCase(home)) {
                            var homeLocation = HoneyAPI.getUser(p.getUniqueId()).getHome(list);
                            if (homeLocation.getWorld() == null) {
                                return false;
                            }
                            p.teleport(homeLocation);
                            p.sendMessage(HoneyAPI.getPrefix() + "§aTeleport to home " + home);
                        }
                    }
                } else {
                    p.sendMessage(HoneyAPI.getPrefix() + "§cHome does not exist");
                }
            }
        }
        return false;
    }

}
