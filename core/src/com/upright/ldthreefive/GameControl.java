package com.upright.ldthreefive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.upright.ldthreefive.logic.Game;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.builder.GameDefinition;
import com.upright.ldthreefive.logic.builder.LevelDefinition;
import com.upright.ldthreefive.logic.levelobjects.player.Player;
import com.upright.ldthreefive.managers.GraphicsManager;
import com.upright.ldthreefive.managers.SoundManager;
import com.upright.ldthreefive.ui.ScreenType;
import com.upright.ldthreefive.ui.options.OptionWindow;

/**
 * Created by Stygian on 4/17/2016.
 */
public class GameControl {
    private LDThreeFive mainGame;
    private boolean debug = false;
    private Stage stage;
    private SoundManager soundManager;
    private GraphicsManager graphicsManager;
    private AssetManager gameAssetManager;
    private Skin skin;
    private OptionWindow optionsWindow;
    private boolean paused;
    private Game game;

    public GameControl(LDThreeFive mainGame) {
        this.mainGame = mainGame;
        gameAssetManager = new AssetManager();
        soundManager = new SoundManager(this);
        graphicsManager = new GraphicsManager(this);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public GraphicsManager getGraphicsManager() {
        return graphicsManager;
    }

    public AssetManager getGameAssetManager() {
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Player getPlayer() {
        return game.getLevel().player;
    }

    public Level getLevel() {
        return game.getLevel();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Game getGame() {
        return game;
    }

    public void switchScreen(ScreenType screenType) {
        mainGame.switchScreen(screenType);
    }

    public void getLoadingAssets() {
        gameAssetManager.load("load-assets.atlas", TextureAtlas.class);
        gameAssetManager.load("sounds/Static.wav", Music.class);
        gameAssetManager.finishLoading();
        graphicsManager.getLoadingAssets();
    }

    public void startGettingGameAssets() {
        gameAssetManager.load("game-assets.atlas", TextureAtlas.class);
        gameAssetManager.load("uiskin.json", Skin.class);
        gameAssetManager.load("sounds/Main.wav", Music.class);
    }

    public void finishGettingGameAssets() {
        Json json = new Json();
        skin = gameAssetManager.get("uiskin.json");
        graphicsManager.finishGettingGameAssets();
        GameDefinition gameDefinition = new GameDefinition();
        gameDefinition.background = "TheEscape";
        gameDefinition.introText = "How you came about you don't know. You awoke, or at least gained consciousness, in a test tube. Poked and prodded by scientists...";
        gameDefinition.levelDefinitionArray = new Array<LevelDefinition>();
        gameDefinition.levelDefinitionArray.add(json.fromJson(LevelDefinition.class, Gdx.files.local("games/Shuttle.json")));
        //Gdx.files.local("games/TheEscape.json").writeString(json.prettyPrint(gameDefinition), false);
        game = new Game(json.fromJson(GameDefinition.class, Gdx.files.local("games/TheEscape.json")));
        mainGame.initializeScreens();
        optionsWindow = new OptionWindow(this);
    }

    public void showOptionWindow(boolean main) {
        optionsWindow.updateState();
        Dialog dialog = new Dialog("Options", skin) {
            public void result(Object object) {
                if ((Boolean) object) {
                    switchScreen(ScreenType.MAIN);
                }
                paused = false;
            }
        };
        dialog.getContentTable().add(optionsWindow);
        dialog.button("Close", false);
        if (!main) {
            dialog.button("Exit to Main", true);
        }
        dialog.show(stage);
    }
}
