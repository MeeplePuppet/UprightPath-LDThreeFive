package com.upright.ldthreefive.ui.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/15/2016.
 */
public class DebugRenderer implements Renderer{
    Box2DDebugRenderer renderer = new Box2DDebugRenderer();

    @Override
    public void renderLevel(OrthographicCamera camera, Level level) {
        renderer.render(level.world, camera.combined);
    }
}
