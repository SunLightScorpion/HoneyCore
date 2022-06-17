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
import org.scorpion.util.Time;

public class CommandBan implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            StringBuilder builder = new StringBuilder();
            if (p.hasPermission(HoneyAPI.getPermission("permission.ban-player"))) {
                if (args.length > 2) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                    HoneyUser user = new HoneyUser(t.getUniqueId());

                    StringBuilder msg = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        msg.append(args[i]).append(" ");
                    }
                    builder.append(msg);

                    if (t.isOnline()) {
                        t.getPlayer().kickPlayer(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.ban-player").replace("%reason%", builder).replace("%time%", "§ePermanent")));
                        user.ban(builder.toString(), -1, Time.NONE);
                    }
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.ban-player-message").replace("%target%", args[0])));
                } else {
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.ban-syntax")));
                }
            } else {
                HoneyAPI.sendNoPermission(p);
            }
        }
        return false;
    }
}
