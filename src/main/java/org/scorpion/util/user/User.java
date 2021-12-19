package org.scorpion.util.user;

import org.bukkit.Location;

import java.util.List;

/**
 * @author Lukas on 11/24/2021
 */
public interface User {

    boolean existPlayer();

    void createUser();

    int getMaxHomes();

    List<String> getHomes();

    Location getHome(String name);

    void addHome(String home);

    void removeHome(String home);

    void saveLocation(String name, Location location);

    void removeLocation(String name);

    String listHomes();

    boolean existHome(String name);

}
