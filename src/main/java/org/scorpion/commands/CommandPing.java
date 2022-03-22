package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.util.Util;

/**
 * @author Lukas on 3/22/2022
 */
public class CommandPing implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            p.sendMessage(Util.getPrefix()+"§aPing: §2"+p.getPing()+"ms");
        }
        return false;
    }

}
