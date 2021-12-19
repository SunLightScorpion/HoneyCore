package org.scorpion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scorpion.user.HoneyUser;
import org.scorpion.util.Util;
import org.scorpion.util.command.ScorpionCommand;

public class CommandHome extends ScorpionCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.sendMessage("Homes: " + new HoneyUser(p.getUniqueId()).listHomes());
            } else if (args.length == 1) {
                String home = args[0];
                if (new HoneyUser(p.getUniqueId()).getHomes().size() > 0) {
                    for (String list : new HoneyUser(p.getUniqueId()).getHomes()) {
                        if (list.equalsIgnoreCase(home)) {
                            var homeLocation = new HoneyUser(p.getUniqueId()).getHome(list);
                            if (homeLocation.getWorld() == null) {
                                return false;
                            }
                            if (homeLocation.getWorld().getName().equalsIgnoreCase("world_monster")) {
                                new HoneyUser(p.getUniqueId()).removeLocation(list);
                                new HoneyUser(p.getUniqueId()).removeHome(list);
                                return false;
                            }
                            p.teleport(homeLocation);
                            p.sendMessage(Util.getPrefix() + "§aTeleport to home " + home);
                        }
                    }
                } else {
                    p.sendMessage(Util.getPrefix() + "§cHome does not exist");
                }
            }
        }
        return false;
    }

    /*
    public CommandHome(String name) {
        super(name);
    }

    @Override
    public void execute(Player p, String[] args) {
        if (args.length == 0) {
            p.openInventory(ServerUI.profileUI(p.getUniqueId()));
            p.sendMessage(BukkitUtil.getPrefix() + new LanguageData(p.getLocale()).translate(MessageType.OPEN_UI));
        } else if (args.length == 1) {
            String home = args[0];
            if (ServerAPI.getUserProfile(p.getUniqueId()).getHomes().size() > 0) {
                for (String list : ServerAPI.getUserProfile(p.getUniqueId()).getHomes()) {
                    if (list.equalsIgnoreCase(home)) {
                        var homeLocation = ServerAPI.getUserProfile(p.getUniqueId()).getHome(list);
                        if (homeLocation.getWorld() == null) {
                            return;
                        }
                        if (homeLocation.getWorld().getName().equalsIgnoreCase("world_monster")) {
                            ServerAPI.getUserProfile(p.getUniqueId()).removeLocation(list);
                            ServerAPI.getUserProfile(p.getUniqueId()).removeHome(list);
                            return;
                        }
                        p.teleport(homeLocation);
                        p.sendMessage(BukkitUtil.getPrefix() + new LanguageData(p.getLocale()).translate(MessageType.HOME_TELEPORT, home));
                    }
                }
            } else {
                p.sendMessage(BukkitUtil.getPrefix() + new LanguageData(p.getLocale()).translate(MessageType.HOME_NOT_EXIST));
            }
        }
    }

    @Override
    public List<String> getCompleteList() {
        return null;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        return null;
    }
     */
}
