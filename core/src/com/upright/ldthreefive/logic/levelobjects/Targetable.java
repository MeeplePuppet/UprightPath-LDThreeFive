package com.upright.ldthreefive.logic.levelobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stygian on 4/15/2016.
 */
public interface Targetable {
    public Vector2 getTargetLocation();

    public boolean isDestroyed();
}
