package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

public class CommandGlow implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("glow"))) {
                if (args.length == 0) {
                    p.setGlowing(!p.isGlowing());
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.glow").replace("%state%", p.isGlowing() + "")));
                }

                if (args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);

                    if (p.hasPermission(HoneyAPI.getPermission("glow.target"))) {
                        if (t == null) {
                            p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.player-not-found").replace("%target%", args[0])));
                            return true;
                        }
                        t.setGlowing(!t.isGlowing());
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.glow-target").replace("%state%", p.isGlowing() + "").replace("%target%", args[0])));
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
