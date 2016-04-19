package com.upright.ldthreefive.logic.levelobjects.agents;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.*;
import com.upright.ldthreefive.logic.levelobjects.player.PlayerState;
import com.upright.ldthreefive.logic.levelobjects.player.PlayerType;

/**
 * Created by Stygian on 4/15/2016.
 */
public abstract class Agent extends MobileLevelObject {
    public Targetable target;
    public AgentState agentState = AgentState.NORMAL;
    public int damage = 1;
    public int attackCoolDown = 120;
    public int curAttackCoolDown = 0;
    protected int turn = 0;
    protected int newTurn = 0;
    public int dir = 1;
    public int timeToCalmDown = 0;

    public float walkSpeed = .5f;
    public float runSpeed = 1f;
    public float turnSpeed = 1f / 120f * MathUtils.PI;

    @Override
    public int getAwareness() {
        return agentState.awareness;
    }

    public boolean moves() {
        return false;
    }

    @Override
    public boolean blocksVision() {
        return true;
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }

    public void damage(int damage) {
        super.damage(damage);
        timeToCalmDown = 120;
    }

    @Override
    public void decideAction(Level level) {
        justAttacked = false;
        if (target != null && target.isDestroyed()) {
            target = null;
        }
        if (canSee(level, level.player)) {
            if (level.player.playerState == PlayerState.TRANSFORMING ||
                    level.player.action == MobileLevelObjectAction.ATTACKING ||
                    !(level.player.playerType == PlayerType.HIDE || level.player.playerType == PlayerType.HUMAN) ||
                    (level.player.playerType == PlayerType.HIDE || level.player.playerType == PlayerType.HUMAN) && level.playerIsTargeted() ||
                    agentState == AgentState.TERRIFIED) {
                if (action != MobileLevelObjectAction.ATTACKING) {
                    target = level.player;
                    action = MobileLevelObjectAction.ATTACKING;
                    curAttackCoolDown = MathUtils.clamp(curAttackCoolDown, attackCoolDown / 2, attackCoolDown);
                }
            }
        } else if (target == level.player) {
            target = null;
        }
        if (target == level.player) {
            if (agentState != AgentState.TERRIFIED) {
                agentState = AgentState.ALERT;
            }
        } else {
            if (agentState == AgentState.ALERT || timeToCalmDown == 0) {
                agentState = AgentState.NORMAL;
            }
        }
    }

    @Override
    public void performAction(Level level) {
        justAttacked = false;
        timeToCalmDown = Math.max(timeToCalmDown - 1, 0);
        Vector2 curLoc = getTargetLocation();
        Vector2 rotation = new Vector2(0, 1).rotate(body.getAngle() * MathUtils.radiansToDegrees);
        float angle = body.getAngle();
        Event nearest = level.getNearestEvent(curLoc);
        if (nearest != null && agentState == AgentState.NORMAL) {
            switch (nearest.eventType) {
                case GUN_SHOT: {
                    agentState = AgentState.ALERT;
                }
                case PLAYER_NOISE: {
                    agentState = AgentState.SCARED;
                    timeToCalmDown = 120;
                }
            }
        }

        if (target != null) {
            Vector2 targetLoc = target.getTargetLocation();
            dir = Intersector.pointLineSide(curLoc, curLoc.cpy().add(rotation), targetLoc);
        }
        switch (action) {
            case ATTACKING: {
                turn = dir;
                body.setTransform(curLoc.x, curLoc.y, angle + dir * this.turnSpeed);
                if (curAttackCoolDown == 0) {
                    shootRaycastCallback.reset();
                    level.world.rayCast(shootRaycastCallback, curLoc, rotation.cpy().scl(10).add(curLoc));
                    level.addEvent(new Event(EventType.ENEMY_NOISE, 9, 5), curLoc);
                    if (shootRaycastCallback.levelObject != null) {
                        shootRaycastCallback.levelObject.damage(damage);
                        level.addEvent(new Event(EventType.GUN_SHOT, 25, 5), shootRaycastCallback.impact);
                    }
                    curAttackCoolDown = attackCoolDown;
                    justAttacked = true;
                } else {
                    curAttackCoolDown--;
                }
                break;
            }
            case WALKING:
            case RUNNING: {
                turn = dir;
                body.setTransform(curLoc.x, curLoc.y, angle + dir * this.turnSpeed);
                if (action == MobileLevelObjectAction.RUNNING) {
                    level.addEvent(new Event(EventType.ENEMY_NOISE, 9, 5), curLoc);
                }
                body.setLinearVelocity(rotation.scl(action == MobileLevelObjectAction.RUNNING ? runSpeed : walkSpeed));
                break;
            }
            case STANDING: {
                body.setTransform(curLoc.x, curLoc.y, angle + dir * this.turnSpeed);
                newTurn = dir;
                if (newTurn == turn && moves()) {
                    action = MobileLevelObjectAction.WALKING;
                }
                body.setLinearVelocity(body.getLinearVelocity().scl(.1f));
                break;
            }
        }
    }

    public int getCurAttackCoolDown() {
        return curAttackCoolDown;
    }
}
