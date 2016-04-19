package com.upright.ldthreefive.logic.levelobjects.agents;

import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObjectAction;

/**
 * Created by Stygian on 4/16/2016.
 */
public class Rotating extends Agent {
    public float minRotation;
    public float maxRotation;

    public Rotating() {
        action = MobileLevelObjectAction.STANDING;
    }

    @Override
    public void decideAction(Level level) {
        if (canSee(level, level.player)) {
            this.target = level.player;
            this.action = MobileLevelObjectAction.ATTACKING;
        } else {
            target = null;
            this.action = MobileLevelObjectAction.STANDING;
        }
    }
}
