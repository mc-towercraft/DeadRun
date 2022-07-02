package me.towercraft.rolles.minigame.deadrun.util.storage;

import me.towercraft.rolles.minigame.deadrun.enumerate.GameState;

public abstract class StorageGameStateDB {

    public static void setState(GameState state) {
        Storage.gameState = state;
    }

    public static GameState getState() {
        return Storage.gameState;
    }
}
