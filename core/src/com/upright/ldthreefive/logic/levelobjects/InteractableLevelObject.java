package com.upright.ldthreefive.logic.levelobjects;


import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stygian on 4/15/2016.
 */
public abstract class InteractableLevelObject extends LevelObject implements Targetable {

    @Override
    public Vector2 getTargetLocation() {
        return body.getPosition();
    }
}
