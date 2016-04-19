package com.upright.ldthreefive;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.upright.ldthreefive.ui.*;

/**
 * Main game class; holds all assets/objects.
 */
public class LDThreeFive extends Game {
    public GameControl gameControl;

    public GameScreen[] gameScreens;

    @Override
    public void create() {
        gameControl = new GameControl(this);
        gameControl.getLoadingAssets();
        gameScreens = new GameScreen[ScreenType.values().length];
        gameScreens[ScreenType.LOADING.ordinal()] = new LoadScreen(gameControl);
        this.setScreen(gameScreens[ScreenType.LOADING.ordinal()]);
        String options = Gdx.app.getPreferences("options").getString("options", null);
        if (options != null) {
            Json json = new Json();
            json.fromJson(Options.class, options).load(gameControl);
        }
    }

    @Override
    public void pause() {
        Options options = new Options();
        options.save(gameControl);
        Json json = new Json();
        Gdx.app.getPreferences("options").putString("options", json.toJson(options));
        Gdx.app.getPreferences("options").flush();
        super.pause();
    }

    public void switchScreen(ScreenType screenType) {
        this.setScreen(gameScreens[screenType.ordinal()]);
    }

    public void initializeScreens() {
        gameScreens[ScreenType.MAIN.ordinal()] = new MainScreen(gameControl);
        gameScreens[ScreenType.SELECT.ordinal()] = new SelectScreen(gameControl);
        gameScreens[ScreenType.LEVEL.ordinal()] = new LevelScreen(gameControl);
    }
}
