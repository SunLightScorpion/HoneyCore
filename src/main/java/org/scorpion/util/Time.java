package org.scorpion.util;

/**
 * @author Lukas on 12/19/2021
 */
public enum Time {

    NONE(0),
    SECOND(1000),
    MINUTE(1000 * 60),
    HOUR(1000 * 60 * 60),
    DAY(1000 * 60 * 60 * 24),
    ;

    long time;

    Time(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

}