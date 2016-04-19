package com.upright.ldthreefive.ui.render.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.GameControl;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObject;

/**
 * Created by Stygian on 4/18/2016.
 */
public class MobSpriteProvider<T extends MobileLevelObject> implements SpriteRenderer<T> {
    public enum AnimationType {
        WALK("Walk"),
        STAND("Stand"),
        ATTACK("Attack"),
        ATTACK_CD("AttackCD"),
        ATTACK_PREP("AttackPrep");
        public String suffix;

        AnimationType(String suffix) {
            this.suffix = suffix;
        }
    }

    protected GameControl gameControl;
    protected Array<Sprite>[] sprite = new Array[AnimationType.values().length];
    protected AnimationType animationType;
    protected String name;
    protected int ticks = 0;
    protected int subTicksNeeded = 4;
    protected int subTicks = 0;

    public MobSpriteProvider(GameControl gameControl, String name) {
        this.gameControl = gameControl;
        this.name = name;
        for (AnimationType animationType : AnimationType.values()) {
            try {
                sprite[animationType.ordinal()] = gameControl.getGraphicsManager().getAnimation(name + animationType.suffix, 1f);
            } catch (NullPointerException npe) {
            }
        }
    }


    @Override
    public Sprite getSprite(T mobileObject) {
        AnimationType previous = animationType;
        if (!(animationType == AnimationType.ATTACK && sprite[animationType.ordinal()].size == ticks)) {
            switch (mobileObject.action) {
                case ATTACKING: {
                    if (mobileObject.justAttacked) {
                        animationType = AnimationType.ATTACK;
                    } else if (mobileObject.getCurAttackCoolDown() > 30) {
                        animationType = AnimationType.ATTACK_CD;
                    } else {
                        animationType = AnimationType.ATTACK_PREP;
                    }
                    break;
                }
                case WALKING:
                case RUNNING: {
                    animationType = AnimationType.WALK;
                    break;
                }
                default: {
                    animationType = AnimationType.STAND;
                }
            }
        }
        if (previous != animationType) {
            ticks = 0;
            subTicks = 0;
        } else {
            subTicks++;
            if (subTicks % subTicksNeeded == 0) {
                ticks++;
                subTicks = 0;
            }
            ticks = ticks % sprite[animationType.ordinal()].size;
        }
        return sprite[animationType.ordinal()].get(ticks);
    }
}
