package com.upright.ldthreefive.logic.levelobjects;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Stygian on 4/15/2016.
 */
public abstract class LevelObject {
    public Body body;

    public int health = 1;

    public void damage(int damage) {
        health = Math.max(health - damage, 0);
    }

    public abstract boolean blocksVision();

    public abstract boolean blocksMovement();

    public boolean isDestroyed() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }
}
