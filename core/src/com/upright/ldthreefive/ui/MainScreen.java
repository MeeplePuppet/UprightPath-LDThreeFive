package com.upright.ldthreefive.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.upright.ldthreefive.GameControl;


/**
 * The main screen for the game.
 */
public class MainScreen extends GameScreen {
    private Image backgroundImage;
    private Image mainImage;
    private Button newGameButton;
    private Button continueGameButton;
    private Button optionsButton;
    private Button exitButton;

    /**
     * Constructor.
     *
     * @param gameControl The main game object.
     */
    public MainScreen(final GameControl gameControl) {
        super(gameControl);
        uiStage.clear();
        Stack stack = new Stack();
        stack.setFillParent(true);
        uiStage.addActor(stack);
        backgroundImage = new Image(gameControl.getGraphicsManager().getSprite("MainBackground"));
        backgroundImage.setFillParent(true);
        stack.addActor(backgroundImage);
        stack.addActor(uiTable);
        mainImage = new Image(gameControl.getGraphicsManager().getSprite("Main"));
        newGameButton = new TextButton("New Game", gameControl.getSkin());
        newGameButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MainScreen.this.gameControl.switchScreen(ScreenType.SELECT);
            }
        });

        optionsButton = new TextButton("Options", gameControl.getSkin());
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameControl.showOptionWindow(true);
            }
        });

        exitButton = new TextButton("Exit", gameControl.getSkin());
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        uiTable.add(mainImage).maxSize(256, 256).minSize(256, 256).colspan(3).row();
        uiTable.add().expandY().colspan(3).row();
        uiTable.add().fillX();
        uiTable.add(newGameButton).fillX();
        uiTable.add().fillX().row();
        uiTable.add().fillX();
        uiTable.add(optionsButton).fillX();
        uiTable.add().fillX().row();
        uiTable.pack();
    }

    @Override
    public void showImplement() {
        gameControl.getSoundManager().playMusic("sounds/Main.wav");
    }

    @Override
    public void hide() {
        gameControl.getSoundManager().stopMusic("sounds/Main.wav");
    }

    @Override
    public void update() {

    }

    @Override
    public void renderImplement(float delta) {

    }
}
