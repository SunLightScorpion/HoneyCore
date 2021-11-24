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

    @Override
    public double getMoney() {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        return Double.parseDouble(file.getString("money"));
    }

    @Override
    public void setMoney(double value) {
        FileManager file = new FileManager("plugins/HoneyCore/User/" + uuid() + ".yml");
        file.set("money", String.valueOf(value));
        file.save();
    }

    @Override
    public void addMoney(double value) {
        setMoney(getMoney() + value);
    }

    @Override
    public void removeMoney(double value) {
        setMoney(getMoney() - value);
    }

    @Override
    public boolean hasEnough(double value) {
        return getMoney() >= value;
    }
}
