package org.scorpion.util.user;

/**
 * @author Lukas on 11/24/2021
 */
public interface User {

    boolean existPlayer();

    void createUser();

    double getMoney();

    void setMoney(double value);

    void addMoney(double value);

    void removeMoney(double value);

    boolean hasEnough(double value);

}
