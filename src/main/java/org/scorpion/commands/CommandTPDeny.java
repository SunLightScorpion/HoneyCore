package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.util.Util;
import org.scorpion.util.command.ScorpionCommand;

public class CommandTPDeny extends ScorpionCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            Util.denyTPA(p);
        }
        return false;
    }

    /*
    public CommandTPDeny(String name) {
        super(name);
    }

    @Override
    public void execute(Player p, String[] args) {
        ServerAPI.denyTPA(p);
    }

    @Override
    public List<String> getCompleteList() {
        return null;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        return null;
    }
     */
}
