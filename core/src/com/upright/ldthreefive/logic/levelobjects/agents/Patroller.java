package com.upright.ldthreefive.logic.levelobjects.agents;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.Utils;
import com.upright.ldthreefive.logic.levelobjects.Event;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObjectAction;
import com.upright.ldthreefive.logic.levelobjects.PathNode;

/**
 * Created by Stygian on 4/15/2016.
 */
public class Patroller extends Agent {
    public Array<PathNode> pathNodeArray = new Array<PathNode>();
    public boolean loop = true;
    public int dir = 1;

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
                target = Utils.getClosestNode(pathNodeArray, curLoc);
            }
            if (agentState == AgentState.NORMAL) {
                action = MobileLevelObjectAction.WALKING;
            } else {
                action = MobileLevelObjectAction.RUNNING;
            }
        } else {
            if (target.getTargetLocation().dst2(getTargetLocation()) < .05) {
                if (target instanceof PathNode) {
                    int index = pathNodeArray.indexOf((PathNode) target, true);
                    index += dir;
                    if (index >= pathNodeArray.size || index < 0) {
                        if (loop) {
                            index = pathNodeArray.size - 1;
                        } else {
                            dir = -dir;
                            index += dir;
                        }
                        action = MobileLevelObjectAction.STANDING;
                    }
                    target = pathNodeArray.get(index);
                }
            }
        }
    }
}
