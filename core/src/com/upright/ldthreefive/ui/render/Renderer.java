package com.upright.ldthreefive.ui.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/15/2016.
 */
public interface Renderer {
    public void renderLevel(OrthographicCamera camera, Level level);
}
