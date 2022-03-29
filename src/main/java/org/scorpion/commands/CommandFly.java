package org.scorpion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;

/**
 * @author Lukas on 12/23/2021
 */
public class CommandFly implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission(HoneyAPI.getPermission("fly"))) {
                if(args.length == 0){
                    if (!p.getAllowFlight()) {
                        p.setAllowFlight(true);
                    } else {
                        p.setFlying(false);
                        p.setAllowFlight(false);
                    }
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.fly-state").replace("%state%", p.getAllowFlight()+"")));
                }
                if(args.length == 1){
                    var o = Bukkit.getPlayer(args[0]);

                    if(o != null){
                        if (!o.getAllowFlight()) {
                            o.setAllowFlight(true);
                        } else {
                            o.setFlying(false);
                            o.setAllowFlight(false);
                        }
                        o.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.fly-state").replace("%state%", p.getAllowFlight()+"")));
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.fly-state-target").replace("%state%", p.getAllowFlight()+"").replace("%target%", o.getName())));
                    } else {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.player-not-found").replace("%target%", args[0])));
                    }
                }
            }
        }
        return false;
    }

}
