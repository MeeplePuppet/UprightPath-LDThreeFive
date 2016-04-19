package com.upright.ldthreefive.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.upright.ldthreefive.ControlAction;
import com.upright.ldthreefive.GameControl;

/**
 * Created by Stygian on 4/13/2016.
 */
public abstract class GameScreen implements Screen {
    /**
     * The main game object; holds loaded assets.
     */
    public GameControl gameControl;

    /**
     * The Stage for ui elements to be draw to.
     */
    public Stage uiStage = new Stage();

    /**
     * The Table for ui elements to reside in.
     */
    public Table uiTable = new Table();

    /**
     * The updateState speed.
     */
    public float tickSpeed = 1f / 60f;

    /**
     * The accumulated ticks.
     */
    public float accum = 0;

    /**
     * Constructor taking the game object.
     *
     * @param gameControl The game control.
     */
    public GameScreen(GameControl gameControl) {
        this.gameControl = gameControl;
        uiStage.addActor(uiTable);
    }

    @Override
    public final void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        accum += delta;
        ControlAction.updateKeys();
        while (accum >= tickSpeed) {
            accum -= tickSpeed;
            update();
        }
        renderImplement(delta);
        uiStage.act();
        uiStage.draw();
    }

    /**
     * Ticks the logic of the screen.
     */
    public abstract void update();


    /**
     * Adapter call for the render operation. Should contain screen specific operations.
     *
     * @param delta
     */
    public abstract void renderImplement(float delta);

    @Override
    public final void show() {
        gameControl.setStage(uiStage);
        Gdx.input.setInputProcessor(uiStage);
        showImplement();
    }

    public abstract void showImplement();

    @Override
    public void resize(int width, int height) {
        uiTable.setSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
