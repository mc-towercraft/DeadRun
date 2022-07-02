package me.towercraft.rolles.minigame.deadrun.util.storage;

public abstract class StoragePlayersCounterDB {

    public static void incrementValue() {
        Storage.playersCounter++;
    }

    public static void decrementValue() {
        Storage.playersCounter--;
    }

    public static Integer getValue() {
        return Storage.playersCounter;
    }
}
