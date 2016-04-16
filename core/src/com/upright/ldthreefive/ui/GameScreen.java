package com.upright.ldthreefive.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.upright.ldthreefive.LDThreeFive;

/**
 * Created by Stygian on 4/13/2016.
 */
public abstract class GameScreen implements Screen {
    /**
     * The main game object; holds loaded assets.
     */
    public LDThreeFive mainGame;

    /**
     * The Stage for ui elements to be draw to.
     */
    public Stage uiStage = new Stage();

    /**
     * The Table for ui elements to reside in.
     */
    public Table uiTable = new Table();

    /**
     * The tick speed.
     */
    public float tickSpeed = 1f / 6f;

    /**
     * The accumulated ticks.
     */
    public float accum = 0;

    /**
     * Constructor taking the game object.
     * @param mainGame The main game.
     */
    public GameScreen(LDThreeFive mainGame)
    {
        this.mainGame = mainGame;
    }

    @Override
    public final void render(float delta) {
        accum += delta;
        while (accum >= tickSpeed)
        {
            accum -= delta;
            tick();
        }
        renderImplement(delta);
        uiStage.draw();
    }

    /**
     * Ticks the logic of the screen.
     */
    public abstract void tick();


    /**
     * Adapter call for the render operation. Should contain screen specific operations.
     * @param delta
     */
    public abstract void renderImplement(float delta);

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
