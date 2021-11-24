package org.scorpion.user;

import org.scorpion.util.file.FileManager;
import org.scorpion.util.user.User;

import java.util.UUID;

/**
 * @author Lukas on 11/24/2021
 */
public record HoneyUser(UUID uuid) implements User {

    @Override
    public boolean existPlayer() {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        return file.exist();
    }

    @Override
    public void createUser() {
        if (!existPlayer()) {
            FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
            file.set("money", "100");
            file.save();
        }
    }

}
