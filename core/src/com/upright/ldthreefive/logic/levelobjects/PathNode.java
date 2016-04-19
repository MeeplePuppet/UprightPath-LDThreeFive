package com.upright.ldthreefive.logic.levelobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Stygian on 4/16/2016.
 */
public class PathNode extends InteractableLevelObject {
    public Vector2 position;
    public Array<PathNode> adjacentNodes = new Array<PathNode>();

    @Override
    public boolean blocksVision() {
        return false;
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
