package com.upright.ldthreefive.logic.levelobjects;

/**
 * Created by Stygian on 4/15/2016.
 */
public class Door extends LevelObject {
    @Override
    public boolean blocksVision() {
        return true;
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
