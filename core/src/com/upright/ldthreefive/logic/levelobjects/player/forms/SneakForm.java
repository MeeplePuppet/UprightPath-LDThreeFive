package com.upright.ldthreefive.logic.levelobjects.player.forms;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.upright.ldthreefive.ControlAction;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.Event;
import com.upright.ldthreefive.logic.levelobjects.EventType;
import com.upright.ldthreefive.logic.levelobjects.InteractableLevelObject;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObjectAction;
import com.upright.ldthreefive.logic.levelobjects.player.Player;
import com.upright.ldthreefive.logic.levelobjects.player.PlayerForm;
import com.upright.ldthreefive.logic.levelobjects.player.PlayerState;

/**
 * Created by Stygian on 4/16/2016.
 */
public class SneakForm extends PlayerForm {

    public SneakForm() {
        this.healthMultiplier = 4;
        this.runSpeed = 2f;
        this.walkSpeed = 1f;
        this.turnSpeed = 1f / 90f * MathUtils.PI;
    }

    @Override
    public void decideAction(Level level, Player player) {
        switch (player.playerState) {
            case NORMAL: {
                if (ControlAction.TRANSFORM.isFirstTrigger()) {
                    player.action = MobileLevelObjectAction.STANDING;
                    player.playerState = PlayerState.TRANSFORMING;
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
        Vector2 curLoc = player.getTargetLocation();
        Vector2 rotation = new Vector2(0, 1).rotate(player.body.getAngle() * MathUtils.radiansToDegrees);
        float angle = player.body.getAngle();
        switch (player.playerState) {
            case NORMAL: {
                switch (player.action) {
                    case ATTACKING: {
                        player.shootRaycastCallback.reset();
                        level.world.rayCast(player.shootRaycastCallback, curLoc, curLoc.cpy().add(rotation));
                        if (player.shootRaycastCallback.levelObject != null) {
                            if (player.shootRaycastCallback.levelObject instanceof InteractableLevelObject) {
                                Vector2 pos = ((InteractableLevelObject) player.shootRaycastCallback.levelObject).getTargetLocation();
                                if (pos.dst2(curLoc) > (.25 * .25)) {
                                    player.shootRaycastCallback.levelObject.damage(1);
                                }
                            }
                        }
                    }
                    case STANDING:
                    case WALKING:
                    case RUNNING: {
                        Vector2 vel;
                        if (ControlAction.LEFT.isTriggered()) {
                            player.body.setTransform(curLoc.x, curLoc.y, angle + turnSpeed);
                        }
                        if (ControlAction.RIGHT.isTriggered()) {
                            player.body.setTransform(curLoc.x, curLoc.y, angle - turnSpeed);
                        }
                        if (ControlAction.UP.isTriggered()) {
                            if (player.action == MobileLevelObjectAction.RUNNING) {
                                vel = rotation.cpy().scl(runSpeed);
                                level.addEvent(new Event(EventType.PLAYER_NOISE, 4, 2), curLoc);
                            } else {
                                vel = rotation.cpy().scl(walkSpeed);
                            }
                        } else if (ControlAction.DOWN.isTriggered()) {
                            vel = player.body.getLinearVelocity().scl(.1f);
                        } else {
                            vel = rotation.cpy().scl(player.body.getLinearVelocity().len() * .5f);
                        }
                        player.body.setLinearVelocity(vel);
                    }
                }
                break;
            }
        }
    }
}
