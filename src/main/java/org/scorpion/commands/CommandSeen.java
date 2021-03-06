package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.user.HoneyUser;

public class CommandSeen implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("seen-target"))) {
                if (args.length == 1) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                    HoneyUser user = new HoneyUser(p.getUniqueId());

                    var data = user.getOnlineTime();

                    if (t.isOnline()) {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.seen-target-online").replace("%target%", args[0]).replace("%time%", data)));
                    } else {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.seen-target-offline").replace("%target%", args[0]).replace("%time%", data)));
                    }
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }
}
