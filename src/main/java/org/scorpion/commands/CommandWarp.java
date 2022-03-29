package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.api.HoneyAPI;
import org.scorpion.util.command.ScorpionCommand;
import org.scorpion.util.warp.Warp;

import java.util.ArrayList;
import java.util.List;

public class CommandWarp extends ScorpionCommand implements TabCompleter {

    List<String> complete = new ArrayList<>();

    /*
    @Override
    public void execute(Player p, String[] args) {
        if (args.length == 1) {
            String data = args[0];
            Warp warp = new Warp(data);
            if (warp.existWarp()) {
                if (warp.getLocation().getWorld() == null) {
                    p.sendMessage(BukkitUtil.getPrefix() + new LanguageData(p.getLocale()).translate(MessageType.WORLD_NOT_FOUND));
                    return;
                }
                warp.teleport(p);
                p.sendMessage(BukkitUtil.getPrefix() + "§7Du hast dich zu §6" + warp.getWarpName() + " §7teleportiert!");
            } else {
                p.sendMessage(BukkitUtil.getPrefix() + "§cDer Warp §e[" + data + "] §cexistiert nicht!");
            }
        } else {
            p.openInventory(getInventory());
        }
    }
     */

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                String data = args[0];
                Warp warp = new Warp(data);
                if (warp.existWarp()) {
                    if (warp.getLocation().getWorld() == null) {
                        p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.world-not-found")));
                        return false;
                    }
                    warp.teleport(p);
                    p.sendMessage(HoneyAPI.getMessage(HoneyAPI.getMessage("message.warp-teleport").replace("%warp%", warp.getWarpName())));
                } else {
                    p.sendMessage(HoneyAPI.getColorCode(HoneyAPI.getMessage("message.warp-not-exist").replace("%warp%", args[0])));
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();
        if (complete.size() != HoneyAPI.getWarps().size()) {
            complete.clear();
        }
        if (sender instanceof Player p) {
            complete.addAll(HoneyAPI.getWarps());
            if (args.length == 1) {
                for (String a : complete) {
                    if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                        result.add(a);
                    }
                }
                return result;
            }
        }
        return null;
    }

}
