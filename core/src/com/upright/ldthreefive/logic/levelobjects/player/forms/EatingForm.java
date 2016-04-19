package com.upright.ldthreefive.logic.levelobjects.player.forms;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.upright.ldthreefive.ControlAction;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.Event;
import com.upright.ldthreefive.logic.levelobjects.EventType;
import com.upright.ldthreefive.logic.levelobjects.InteractableLevelObject;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObjectAction;
import com.upright.ldthreefive.logic.levelobjects.agents.Agent;
import com.upright.ldthreefive.logic.levelobjects.player.Player;
import com.upright.ldthreefive.logic.levelobjects.player.PlayerForm;
import com.upright.ldthreefive.logic.levelobjects.player.PlayerState;

/**
 * Created by Stygian on 4/16/2016.
 */
public class EatingForm extends PlayerForm {
    public int damage;
    public int attackCoolDown;
    public int curAttackCoolDown = 0;

    public EatingForm() {
        this.healthMultiplier = 1;
        this.runSpeed = 1.5f;
        this.walkSpeed = .75f;
        this.turnSpeed = 1f / 60f * MathUtils.PI;
        this.damage = 1;
        this.attackCoolDown = 90;
    }

    @Override
    public void decideAction(Level level, Player player) {
        switch (player.playerState) {
            case NORMAL: {
                if (ControlAction.TRANSFORM.isFirstTrigger()) {
                    player.action = MobileLevelObjectAction.STANDING;
                    player.playerState = PlayerState.TRANSFORMING;
                } else if (ControlAction.ATTACK.isTriggered()) {
                    player.action = MobileLevelObjectAction.ATTACKING;
                    player.playerState = PlayerState.NORMAL;
                } else if (ControlAction.UP.isTriggered()) {
                    if (ControlAction.RUN.isTriggered()) {
                        player.action = MobileLevelObjectAction.RUNNING;
                    } else {
                        player.action = MobileLevelObjectAction.WALKING;
                    }
                    player.playerState = PlayerState.NORMAL;
                } else {
                    player.action = MobileLevelObjectAction.STANDING;
                }
                break;
            }
        }
    }

    @Override
    public void performAction(Level level, Player player) {
        curAttackCoolDown = Math.max(curAttackCoolDown - 1, 0);
        Vector2 curLoc = player.getTargetLocation();
        Vector2 rotation = new Vector2(0, 1).rotate(player.body.getAngle() * MathUtils.radiansToDegrees);
        float angle = player.body.getAngle();
        switch (player.playerState) {
            case NORMAL: {
                switch (player.action) {
                    case ATTACKING: {
                        if (curAttackCoolDown <= 0) {
                            player.shootRaycastCallback.reset();
                            Vector2 attack = curLoc.cpy().add(rotation);
                            level.world.rayCast(player.shootRaycastCallback, curLoc, attack);
                            if (player.shootRaycastCallback.levelObject != null) {
                                if (player.shootRaycastCallback.levelObject instanceof InteractableLevelObject) {
                                    Vector2 pos = ((InteractableLevelObject) player.shootRaycastCallback.levelObject).getTargetLocation();
                                    if (pos.dst2(curLoc) < 1f) {
                                        int targetHealth = Math.min(player.shootRaycastCallback.levelObject.getHealth(), damage);
                                        player.shootRaycastCallback.levelObject.damage(damage);
                                        curAttackCoolDown = attackCoolDown;
                                        if (player.shootRaycastCallback.levelObject instanceof Agent) {
                                            player.damage(-targetHealth);
                                            player.essenseLevel = targetHealth;
                                            level.addEvent(new Event(EventType.PLAYER_NOISE, 25, 10), curLoc);
                                        }
                                    }
                                }
                            }
                            if (curAttackCoolDown == 0) {
                                curAttackCoolDown = attackCoolDown / 2;
                            }
                            player.justAttacked = true;
                        }
                    }
                    case STANDING: {
                    }
                    case WALKING:
                    case RUNNING: {
                        Vector2 vel;
                        if (player.action == MobileLevelObjectAction.RUNNING) {
                            level.addEvent(new Event(EventType.PLAYER_NOISE, 9, 3), curLoc);
                        }
                        if (ControlAction.LEFT.isTriggered()) {
                            player.body.setTransform(curLoc.x, curLoc.y, angle + turnSpeed);
                        }
                        if (ControlAction.RIGHT.isTriggered()) {
                            player.body.setTransform(curLoc.x, curLoc.y, angle - turnSpeed);
                        }
                        if (ControlAction.UP.isTriggered()) {
                            if (player.action == MobileLevelObjectAction.RUNNING) {
                                vel = rotation.cpy().scl(runSpeed);
                            } else {
                                vel = rotation.cpy().scl(walkSpeed);
                            }
                        } else if (ControlAction.DOWN.isTriggered()) {
                            vel = player.body.getLinearVelocity().scl(.1f);
                        } else {
                            vel = rotation.cpy().scl(player.body.getLinearVelocity().len());
                        }
                        player.body.setLinearVelocity(vel);
                    }
                }
                break;
            }
        }
    }

    @Override
    public int getCurAttackCoolDown() {
        return curAttackCoolDown;
    }

    @Override
    public int getAttackCoolDown() { return attackCoolDown; };
}
