package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.user.HoneyUser;

public class CommandGod implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("god"))) {
                if (args.length == 0) {
                    HoneyUser user = new HoneyUser(p.getUniqueId());
                    user.setGod(!user.getGod().equalsIgnoreCase("true"));

                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.god").replace("%state%", user.getGod())));
                }

                if (args.length == 1) {
                    if (p.hasPermission(HoneyAPI.getPermission("god-target"))) {
                        Player t = Bukkit.getPlayer(args[0]);
                        assert t != null;

                        HoneyUser user = new HoneyUser(t.getUniqueId());

                        user.setGod(!user.getGod().equalsIgnoreCase("true"));

                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.god-target").replace("%state%", user.getGod()).replace("%target%", t.getName())));
                    } else {
                        HoneyAPI.sendNoPermission(p);
                    }
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }
}
