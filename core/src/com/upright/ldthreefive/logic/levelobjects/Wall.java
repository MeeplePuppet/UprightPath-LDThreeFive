package com.upright.ldthreefive.logic.levelobjects;

public class Wall extends LevelObject {

    @Override
    public void damage(int damage) {
    }

    @Override
    public boolean blocksVision() {
        return true;
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }
}
