package com.upright.ldthreefive.logic.levelobjects.player;

import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/16/2016.
 */
public abstract class PlayerForm {
    public int healthMultiplier;
    public float runSpeed;
    public float walkSpeed;
    public float turnSpeed;
    public int transformationTime = 60;
    public int maxTransformationLevel = 1;


    public abstract void decideAction(Level level, Player player);

    public abstract void performAction(Level level, Player player);

    public int getCurAttackCoolDown() {
        return -1;
    }

    public int getAttackCoolDown() {
        return -1;
    }
}
