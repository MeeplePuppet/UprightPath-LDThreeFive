package com.upright.ldthreefive.logic.levelobjects.agents;

import com.badlogic.gdx.math.Vector2;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.Utils;
import com.upright.ldthreefive.logic.levelobjects.Event;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObjectAction;

/**
 * Created by Stygian on 4/16/2016.
 */
public class Stationed extends Agent {

    @Override
    public boolean moves() {
        return true;
    }

    @Override
    public void decideAction(Level level) {
        Vector2 curLoc = getTargetLocation();
        super.decideAction(level);
        Event nearestEvent = level.getNearestEvent(curLoc);
        if (target != null && target != level.player && nearestEvent != null) {
            target = nearestEvent;
        }
        if (target == null) {
            if (target == null) {
                target = Utils.getClosestNode(level.pathNodes, curLoc);
            }
            if (agentState == AgentState.NORMAL) {
                action = MobileLevelObjectAction.WALKING;
            } else {
                action = MobileLevelObjectAction.RUNNING;
            }
        }
    }
}
