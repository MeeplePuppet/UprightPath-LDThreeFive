package com.upright.ldthreefive.logic.builder;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stygian on 4/16/2016.
 */
public class PathNodeDefinition {
    Vector2 position;
    int[] adjacentNodes;

    public PathNodeDefinition() {
    }

    public PathNodeDefinition(Vector2 position, int[] adjacentNodes) {
        this.position = position;
        this.adjacentNodes = adjacentNodes;
    }
}
