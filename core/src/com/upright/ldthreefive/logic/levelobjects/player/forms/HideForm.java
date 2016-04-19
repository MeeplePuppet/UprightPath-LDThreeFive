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

public class HideForm extends PlayerForm {
    public int damage;
    public int attackCoolDown;
    public int curAttackCoolDown = 0;

    public HideForm() {
        this.healthMultiplier = 2;
        this.runSpeed = 2f;
        this.walkSpeed = 1f;
        this.turnSpeed = 1f / 90f * MathUtils.PI;
        this.attackCoolDown = 120;
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
                                    if (pos.dst2(curLoc) < 2.25f) {
                                        int targetHealth = Math.min(player.shootRaycastCallback.levelObject.getHealth(), damage);
                                        player.shootRaycastCallback.levelObject.damage(damage);
                                        curAttackCoolDown = attackCoolDown;
                                        if (player.shootRaycastCallback.levelObject instanceof Agent) {
                                            player.damage(-targetHealth);
                                            player.essenseLevel += targetHealth;
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
    public int getAttackCoolDown() {
        return attackCoolDown;
    }

    ;
}
