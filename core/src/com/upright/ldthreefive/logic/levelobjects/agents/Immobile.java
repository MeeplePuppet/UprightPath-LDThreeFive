package com.upright.ldthreefive.logic.levelobjects.agents;

import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObjectAction;

/**
 * Created by Stygian on 4/16/2016.
 */
public class Immobile extends Agent {
    @Override
    public void decideAction(Level level) {
        if (canSee(level, level.player)) {
            this.action = MobileLevelObjectAction.ATTACKING;
        } else {
            this.body.setTransform(getTargetLocation(), turnSpeed);
        }
    }
}
