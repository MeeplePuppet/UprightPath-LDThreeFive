package com.upright.ldthreefive.logic.levelobjects.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.upright.ldthreefive.ControlAction;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObject;
import com.upright.ldthreefive.logic.levelobjects.player.forms.EatingForm;
import com.upright.ldthreefive.logic.levelobjects.player.forms.HideForm;
import com.upright.ldthreefive.logic.levelobjects.player.forms.HumanForm;
import com.upright.ldthreefive.logic.levelobjects.player.forms.SneakForm;

/**
 * Created by Stygian on 4/15/2016.
 */
public class Player extends MobileLevelObject {
    public int transformTime = 0;
    public int transformLevel = 0;
    public int essenseLevel = 0;
    public PlayerType playerType = PlayerType.SNEAK;
    public PlayerType nextPlayerType = PlayerType.EATING;
    public PlayerState playerState = PlayerState.NORMAL;
    public PlayerForm[] playerForm;
    public Body sneakBody;
    public Body bigBody;
    public Body hideBody;

    public Player() {
        health = 4;
        playerForm = new PlayerForm[PlayerType.values().length];
        playerForm[PlayerType.EATING.ordinal()] = new EatingForm();
        playerForm[PlayerType.HIDE.ordinal()] = new HideForm();
        playerForm[PlayerType.HUMAN.ordinal()] = new HumanForm();
        playerForm[PlayerType.SNEAK.ordinal()] = new SneakForm();
    }

    public void computeStats() {
        health = 4 * (Upgrade.HEALTH.getLevel() + 1);
        playerForm[PlayerType.SNEAK.ordinal()].transformationTime = 180 / (Upgrade.SNEAK_TRANSFORM.getLevel() + 1);
        playerForm[PlayerType.HUMAN.ordinal()].transformationTime = 320 / (Upgrade.HUMAN_TRANSFORM.getLevel() + 1);
        ((HumanForm) playerForm[PlayerType.HUMAN.ordinal()]).damage = 1 * (Upgrade.HUMAN_DAMAGE.getLevel() + 1);
        ((HumanForm) playerForm[PlayerType.HUMAN.ordinal()]).attackCoolDown = 180 / (Upgrade.HUMAN_COOLDOWN.getLevel() + 1);
        playerForm[PlayerType.HIDE.ordinal()].transformationTime = 320 / (Upgrade.HIDE_TRANSFORM.getLevel() + 1);
        ((HideForm) playerForm[PlayerType.HIDE.ordinal()]).damage = 2 * (Upgrade.HIDE_DAMAGE.getLevel() + 1);
        ((HideForm) playerForm[PlayerType.HIDE.ordinal()]).attackCoolDown = 90 / (Upgrade.HIDE_COOLDOWN.getLevel() + 1);
        playerForm[PlayerType.EATING.ordinal()].transformationTime = 180 / (Upgrade.EATING_TRANSFORM.getLevel() + 1);
        ((EatingForm) playerForm[PlayerType.EATING.ordinal()]).damage = 1 * (Upgrade.EATING_DAMAGE.getLevel() + 1);
        ((EatingForm) playerForm[PlayerType.EATING.ordinal()]).attackCoolDown = 90 / (Upgrade.EATING_COOLDOWN.getLevel() + 1);
    }

    @Override
    public int getHealth() {
        return (int) Math.ceil(health / (float) playerForm[playerType.ordinal()].healthMultiplier);
    }

    @Override
    public void damage(int damage) {
        health -= playerForm[playerType.ordinal()].healthMultiplier * damage;
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }

    @Override
    public int getAwareness() {
        return 20;
    }

    public void swapNextPlayerType() {
        nextPlayerType = PlayerType.values()[(nextPlayerType.ordinal() + 1) % PlayerType.values().length];
        System.out.println("Next: " + nextPlayerType);
    }

    public void updatePlayerType() {
        sneakBody.setActive(false);
        bigBody.setActive(false);
        hideBody.setActive(false);
        switch (playerType) {
            case HUMAN:
            case EATING: {
                body = bigBody;
                break;
            }
            case HIDE: {
                body = hideBody;
                break;
            }
            case SNEAK: {
                body = sneakBody;
                break;
            }
        }
        body.setActive(true);
    }

    public void transform() {
        if (transformTime > playerForm[nextPlayerType.ordinal()].transformationTime) {
            transformLevel = Math.min(playerForm[nextPlayerType.ordinal()].maxTransformationLevel, transformTime / playerForm[nextPlayerType.ordinal()].transformationTime);
            playerType = nextPlayerType;
            updatePlayerType();
        }
        transformTime = 0;
    }

    @Override
    public boolean blocksVision() {
        return true;
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }

    @Override
    public void decideAction(Level level) {
        justAttacked = false;
        if (ControlAction.SWAP_TRANS.isFirstTrigger()) {
            swapNextPlayerType();
        }
        playerForm[playerType.ordinal()].decideAction(level, this);
    }

    @Override
    public void performAction(Level level) {
        switch (playerState) {
            case TRANSFORMING: {
                if (ControlAction.TRANSFORM.isTriggered() && transformTime <= playerForm[nextPlayerType.ordinal()].transformationTime) {
                    transformTime++;
                } else {
                    transform();
                    playerState = PlayerState.NORMAL;
                }
                break;
            }
            case EATING:
            case NORMAL: {
                playerForm[playerType.ordinal()].performAction(level, this);
                break;
            }
        }
    }

    public void updatePositions() {
        Vector2 position = body.getPosition();
        float rotation = body.getAngle();
        Vector2 velocity = body.getLinearVelocity();

        sneakBody.setTransform(position, rotation);
        sneakBody.setLinearVelocity(velocity);
        bigBody.setTransform(position, rotation);
        bigBody.setLinearVelocity(velocity);
        hideBody.setTransform(position, rotation);
        hideBody.setLinearVelocity(velocity);
    }

    @Override
    public int getCurAttackCoolDown() {
        return playerForm[playerType.ordinal()].getCurAttackCoolDown();
    }

    public int getAttackCoolDown() {
        return playerForm[playerType.ordinal()].getAttackCoolDown();
    }

    public int getTransformTime() {
        return playerForm[playerType.ordinal()].transformationTime;
    }
}
