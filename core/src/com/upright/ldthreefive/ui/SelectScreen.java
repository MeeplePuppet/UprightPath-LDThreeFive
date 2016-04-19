package com.upright.ldthreefive.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.upright.ldthreefive.GameControl;
import com.upright.ldthreefive.logic.builder.LevelDefinition;

/**
 * Created by Stygian on 4/17/2016.
 */
public class SelectScreen extends GameScreen {
    private Window window;
    private Label essenceCount;
    private Image backgroundImage;
    private TextButton buyUpgradesButton;
    private TextButton optionsButton;
    private TextButton exitButton;
    private UpgradeTable upgradeTable;

    public SelectScreen(final GameControl gameControl) {
        super(gameControl);
        uiTable.setFillParent(true);
        window = new Window(gameControl.getGame().getGameDefinition().title, gameControl.getSkin());
        essenceCount = new Label(gameControl.getGame().getGameDefinition().background, gameControl.getSkin());
        buyUpgradesButton = new TextButton("Buy Upgrades", gameControl.getSkin());
        optionsButton = new TextButton("Options", gameControl.getSkin());
        exitButton = new TextButton("Exit", gameControl.getSkin());
        window.add(essenceCount).fillX().expandX().growX();
        window.add(buyUpgradesButton);
        window.add(optionsButton);
        upgradeTable = new UpgradeTable(gameControl);
        buyUpgradesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog dialog = new Dialog("Purchase Upgrades", gameControl.getSkin()) {
                    @Override
                    public void result(Object object) {
                        updateState();
                    }
                };
                dialog.setSize(400, 400);
                upgradeTable.update();
                dialog.getContentTable().add(upgradeTable);
                dialog.button("Close");
                dialog.show(uiStage);
            }
        });
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameControl.showOptionWindow(false);
            }
        });
        backgroundImage = new Image(gameControl.getGraphicsManager().getSprite(gameControl.getGame().getGameDefinition().background));

        uiTable.add(window).fillX().expandX().row();
        uiTable.add(backgroundImage).fill().expand().grow();
    }

    @Override
    public void update() {

    }

    @Override
    public void renderImplement(float delta) {

    }

    @Override
    public void showImplement() {
        uiStage.clear();
        uiStage.addActor(uiTable);
        for (int i = 0; i < gameControl.getGame().getGameDefinition().levelDefinitionArray.size; i++) {
            final LevelDefinition levelDefinition = gameControl.getGame().getGameDefinition().levelDefinitionArray.get(i);
            Image image = new Image(gameControl.getGraphicsManager().getAnimation(gameControl.getGame().getGameDefinition().background + "Levels").get(i));
            image.setScale(4);
            final int finalI = i;
            image.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Dialog dialog = new Dialog(levelDefinition.levelName, gameControl.getSkin()) {
                        @Override
                        public void result(Object object) {
                            if ((Boolean) object) {
                                gameControl.getGame().loadLevel(finalI);
                                gameControl.switchScreen(ScreenType.LEVEL);
                            }
                        }
                    };
                    TextArea textArea = new TextArea(levelDefinition.description, gameControl.getSkin());
                    textArea.setFillParent(true);
                    dialog.getContentTable().add(textArea).size(300, 200).grow();
                    dialog.button("Start", true);
                    dialog.button("Cancel", false);
                    dialog.invalidate();
                    dialog.layout();
                    dialog.show(uiStage);
                    return false;
                }
            });
            image.setPosition(levelDefinition.posX, levelDefinition.posY);
            uiStage.addActor(image);
        }

        if (!gameControl.getGame().isDisplayedIntro()) {
            Dialog dialog = new Dialog("Introduction", gameControl.getSkin());
            TextArea textArea = new TextArea(gameControl.getGame().getGameDefinition().introText, gameControl.getSkin());
            textArea.setFillParent(true);
            dialog.getContentTable().add(textArea).size(300, 200).grow();
            dialog.key(Input.Keys.ANY_KEY, true);
            dialog.button("Close");
            gameControl.getGame().setDisplayedIntro(true);
            dialog.invalidate();
            dialog.layout();
            dialog.show(uiStage);
        }
        updateState();
    }

    public void updateState() {
        essenceCount.setText("Essence Points: " + gameControl.getGame().getEssenceLevel());
    }
}
