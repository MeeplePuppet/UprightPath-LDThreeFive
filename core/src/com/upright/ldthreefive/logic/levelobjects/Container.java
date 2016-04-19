package com.upright.ldthreefive.logic.levelobjects;

import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/15/2016.
 */
public class Container extends MobileLevelObject {
    @Override
    public boolean blocksVision() {
        return false;
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }

    @Override
    public int getAwareness() {
        return 0;
    }

    @Override
    public void decideAction(Level level) {

    }

    @Override
    public void performAction(Level level) {

    }
}
