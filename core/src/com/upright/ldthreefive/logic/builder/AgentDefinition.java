package com.upright.ldthreefive.logic.builder;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stygian on 4/16/2016.
 */
public class AgentDefinition {
    public Vector2 position;
    public int damage = 1;
    public int attackCoolDown = 120;
    public int health = 1;
    public float turnSpeed = 1f / 60f * MathUtils.PI;
    public float rotation = 0f;
}
