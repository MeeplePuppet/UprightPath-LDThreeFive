package com.upright.ldthreefive.managers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.upright.ldthreefive.GameControl;

/**
 * Created by Stygian on 4/17/2016.
 */
public class GraphicsManager {
    private GameControl gameControl;
    private ObjectMap<String, Sprite> spriteObjectMap = new ObjectMap<String, Sprite>();
    private ObjectMap<String, Array<Sprite>> animationObjectMap = new ObjectMap<String, Array<Sprite>>();
    private TextureAtlas gameTextureAtlas;
    private TextureAtlas loadTextureAtlas;

    public GraphicsManager(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    public void getLoadingAssets() {
        loadTextureAtlas = gameControl.getGameAssetManager().get("load-assets.atlas");
    }

    public void finishGettingGameAssets() {
        gameTextureAtlas = gameControl.getGameAssetManager().get("game-assets.atlas");
    }

    public Sprite getSprite(String spriteName, float size) {
        Sprite sprite = getSprite(spriteName);
        sprite.setSize(size, size);
        return sprite;
    }

    public Sprite getSprite(String spriteName) {
        Sprite sprite = spriteObjectMap.get(spriteName);
        if (sprite == null) {
            try {
                sprite = gameTextureAtlas.createSprite(spriteName);
            } catch (NullPointerException npe) {
            }
            if (sprite == null) {
                sprite = loadTextureAtlas.createSprite(spriteName);
            }
            spriteObjectMap.put(spriteName, sprite);
        }
        return new Sprite(sprite);
    }

    public Array<Sprite> getAnimation(String animationName, float size) {
        Array<Sprite> animation = getAnimation(animationName);
        for (Sprite sprite : animation) {
            sprite.setSize(size, size);
        }
        return animation;
    }

    public Array<Sprite> getAnimation(String animationName) {
        Array<Sprite> animation = animationObjectMap.get(animationName), newAnimation;
        if (animation == null) {
            animation = new Array<Sprite>();
            TextureRegion region = null;
            try {
                region = gameTextureAtlas.findRegion(animationName);
            } catch (NullPointerException npe) {
            }
            if (region == null) {
                region = loadTextureAtlas.findRegion(animationName);
            }
            for (TextureRegion animationRegion : region.split(region.getRegionHeight(), region.getRegionHeight())[0]) {
                animation.add(new Sprite(animationRegion));
            }
            animationObjectMap.put(animationName, animation);
        }
        newAnimation = new Array<Sprite>();
        for (Sprite sprite : animation) {
            newAnimation.add(new Sprite(sprite));
        }
        return newAnimation;
    }
}
