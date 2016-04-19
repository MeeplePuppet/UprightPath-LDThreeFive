package com.upright.ldthreefive.ui.render.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObject;

/**
 * Created by Stygian on 4/18/2016.
 */
public interface SpriteRenderer<T extends MobileLevelObject> {
    public Sprite getSprite(T mobileObject);
}
