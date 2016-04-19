package com.upright.ldthreefive.ui.render;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.upright.ldthreefive.GameControl;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.Container;
import com.upright.ldthreefive.logic.levelobjects.Event;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObject;
import com.upright.ldthreefive.logic.levelobjects.agents.Patroller;
import com.upright.ldthreefive.logic.levelobjects.agents.Stationed;
import com.upright.ldthreefive.logic.levelobjects.player.Player;
import com.upright.ldthreefive.ui.render.objects.MobSpriteProvider;
import com.upright.ldthreefive.ui.render.objects.PlayerRenderer;
import com.upright.ldthreefive.ui.render.objects.SpriteRenderer;

/**
 * Created by Stygian on 4/17/2016.
 */
public class ImageRenderer implements Renderer {
    public int ticks = 0;
    GameControl gameControl;
    RayHandler rayHandler;
    PointLight pointLight;
    SpriteBatch spriteBatch;
    Sprite backgroundImage;
    Sprite container;
    ObjectMap<Class, SpriteRenderer> renderers = new ObjectMap<Class, SpriteRenderer>();
    Array<Sprite> events = new Array<Sprite>();

    public ImageRenderer(GameControl gameControl) {
        this.gameControl = gameControl;
        spriteBatch = new SpriteBatch();
        rayHandler = new RayHandler(gameControl.getLevel().world);
        pointLight = new PointLight(rayHandler, 32, new Color(1, 1, 1, .5f), 10f, 0f, 0f);
        pointLight.setSoft(true);
        pointLight.setIgnoreAttachedBody(true);
        pointLight.attachToBody(gameControl.getPlayer().body);
        backgroundImage = gameControl.getGraphicsManager().getSprite(gameControl.getLevel().image);
        backgroundImage.setBounds(gameControl.getLevel().offsetX, gameControl.getLevel().offsetY, backgroundImage.getWidth() / 32, backgroundImage.getHeight() / 32);
        backgroundImage.setFlip(false, true);
        renderers.put(Container.class, new MobSpriteProvider<Container>(gameControl, "Container"));
        renderers.put(Patroller.class, new MobSpriteProvider<Container>(gameControl, "Guard"));
        renderers.put(Stationed.class, new MobSpriteProvider<Container>(gameControl, "Guard"));
        renderers.put(Player.class, new PlayerRenderer(gameControl));
        events = gameControl.getGraphicsManager().getAnimation("Events", .2f);
    }

    @Override
    public void renderLevel(OrthographicCamera camera, Level level) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        backgroundImage.draw(spriteBatch);
        Sprite sprite;
        Vector2 pos;
        for (int i = 0; i < level.mobiles.size; i++) {
            MobileLevelObject mob = level.mobiles.get(i);
            drawMob(mob);
        }
        spriteBatch.end();
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
        spriteBatch.begin();
        drawMob(level.player);
        for (int i = 0; i < level.events.size; i++) {
            Event event = level.events.get(i);
            pos = event.getTargetLocation();
            switch (event.eventType) {
                case PLAYER_NOISE: {
                    sprite = events.get(0);
                    break;
                }
                case ENEMY_NOISE: {
                    sprite = events.get(1);
                    break;
                }
                default: {
                    sprite = events.get(2);
                    break;
                }
            }
            sprite.setPosition(pos.x - .1f, pos.y - .1f);
            sprite.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    public void drawMob(MobileLevelObject mob) {
        Sprite sprite;
        Vector2 pos;
        pos = mob.getTargetLocation();
        float rotation = mob.body.getAngle() * MathUtils.radiansToDegrees;
        sprite = renderers.get(mob.getClass()).getSprite(mob);
        sprite.setPosition(pos.x - .5f, pos.y - .5f);
        sprite.setOriginCenter();
        sprite.setRotation(rotation);
        sprite.draw(spriteBatch);
    }
}
