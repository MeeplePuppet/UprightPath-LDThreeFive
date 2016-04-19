package com.upright.ldthreefive.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.logic.levelobjects.PathNode;

/**
 * Created by Stygian on 4/16/2016.
 */
public class Utils {
    public static PathNode getClosestNode(Array<PathNode> nodes, Vector2 startPosition) {
        PathNode closestNode = null;
        float closestDistance = Float.MAX_VALUE;
        for (PathNode pathNode : nodes) {
            float distance = startPosition.dst2(pathNode.position);
            if (distance < closestDistance) {
                closestNode = pathNode;
                closestDistance = distance;
            }
        }
        return closestNode;
    }
}
