package com.upright.ldthreefive.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.upright.ldthreefive.ControlAction;
import com.upright.ldthreefive.GameControl;
import com.upright.ldthreefive.logic.levelobjects.player.PlayerState;
import com.upright.ldthreefive.ui.render.DebugRenderer;
import com.upright.ldthreefive.ui.render.ImageRenderer;
import com.upright.ldthreefive.ui.render.Renderer;

/**
 * Created by Stygian on 4/15/2016.
 */
public class LevelScreen extends GameScreen {
    ImageRenderer renderer;
    Renderer debugRenderer;
    OrthographicCamera camera;
    SpriteBatch spriteBatch = new SpriteBatch();
    BitmapFont bitmapFont = new BitmapFont();
    float height;
    float width;
    Window window;
    Label healthValueLabel;
    Label essenceValueLabel;
    Table formTable;
    ProgressBar transformProgressBar;
    Table attackTable;
    ProgressBar attackCoolDownProgressBar;

    public LevelScreen(GameControl gameControl) {
        super(gameControl);
        debugRenderer = new DebugRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera(10, 10 * (height / width));

        window = new Window("", gameControl.getSkin());
        Sprite sprite = gameControl.getGraphicsManager().getAnimation("icons", 32).get(1);
        healthValueLabel = new Label("", gameControl.getSkin());
        essenceValueLabel = new Label("", gameControl.getSkin());
        formTable = new Table(gameControl.getSkin());
        transformProgressBar = new ProgressBar(0f, 1f, .01f, false, gameControl.getSkin());
        attackTable = new Table(gameControl.getSkin());
        attackCoolDownProgressBar = new ProgressBar(0f, 1f, .01f, false, gameControl.getSkin());

        uiTable.setFillParent(true);
        uiTable.add(window);
        window.add("HP:");
        window.add(healthValueLabel).row();
        window.add("EP:");
        window.add(essenceValueLabel).row();
        window.add("FM:");
        window.add(formTable).row();
        window.add("AT:");


        uiTable.add().fillX().row();
        uiTable.add().expand().grow().fill();
    }

    @Override
    public void update() {
        if (ControlAction.CANCEL.isFirstTrigger()) {
            gameControl.setPaused(true);
            gameControl.showOptionWindow(false);
        }
        if (!gameControl.isPaused()) {
            gameControl.getLevel().update();
            camera = new OrthographicCamera(width, height);
            Vector2 pos = gameControl.getPlayer().getTargetLocation();
            camera.position.set(pos.x, pos.y, 2);
            camera.rotate(Vector3.Z, gameControl.getPlayer().body.getAngle() * MathUtils.radiansToDegrees);
            camera.update();
            gameControl.getLevel().world.step(tickSpeed, 6, 2);
            renderer.ticks++;
            healthValueLabel.setText(gameControl.getGame().getLevel().player.getHealth() + "");
            essenceValueLabel.setText(gameControl.getGame().getLevel().player.essenseLevel + "");
            formTable.clear();
            if (gameControl.getGame().getLevel().player.playerState == PlayerState.TRANSFORMING) {
                formTable.add(transformProgressBar);
                transformProgressBar.setValue(1f - ((float) gameControl.getGame().getLevel().player.transformTime / (float) gameControl.getGame().getLevel().player.getTransformTime()));
            } else {
                formTable.add(gameControl.getGame().getLevel().player.nextPlayerType.getName());
            }

            int cooldown = gameControl.getPlayer().getCurAttackCoolDown();
            attackTable.clear();
            if (cooldown < 0) {
                attackTable.add("NONE");
            } else if (cooldown > 0) {
                attackTable.add(attackCoolDownProgressBar);
                attackCoolDownProgressBar.setValue(1 - ((float) cooldown / (float) gameControl.getPlayer().getAttackCoolDown()));
            } else {
                attackTable.add("READY");
            }
            healthValueLabel.setText("" + gameControl.getPlayer().getHealth());
            if (gameControl.getLevel().gameLost()) {
                gameControl.setPaused(true);
                Dialog message = new Dialog("Lost", gameControl.getSkin()) {
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            gameControl.getGame().loadLevel();
                            gameControl.switchScreen(ScreenType.LEVEL);
                            gameControl.setPaused(false);
                        } else {
                            gameControl.switchScreen(ScreenType.SELECT);
                            gameControl.setPaused(false);
                        }
                    }
                };
                message.text("You've died!");
                message.button("Retry", true);
                message.button("Exit", false);
                message.invalidateHierarchy();
                message.invalidate();
                message.layout();
                message.show(uiStage);
            } else if (gameControl.getLevel().gameWon()) {
                gameControl.setPaused(true);
                Dialog message = new Dialog("Level Complete", gameControl.getSkin()) {
                    protected void result(Object object) {
                        gameControl.getGame().finishLevel();
                        gameControl.switchScreen(ScreenType.SELECT);
                        gameControl.setPaused(false);
                    }
                };
                message.text(gameControl.getLevel().winCondition.getWinDescription());
                message.button("Continue");
                message.invalidateHierarchy();
                message.invalidate();
                message.layout();
                message.show(uiStage);
            }
        }
    }

    @Override
    public void renderImplement(float delta) {
        renderer.renderLevel(camera, gameControl.getLevel());
        if (gameControl.isDebug()) {
            debugRenderer.renderLevel(camera, gameControl.getLevel());
        }
        spriteBatch.begin();
        bitmapFont.draw(spriteBatch, "HP: " + gameControl.getPlayer().getHealth(), 0, 40);
        bitmapFont.draw(spriteBatch, "ES: " + gameControl.getPlayer().essenseLevel, 0, 30);
        bitmapFont.draw(spriteBatch, "Ty: " + gameControl.getPlayer().playerType, 0, 20);
        bitmapFont.draw(spriteBatch, "St: " + gameControl.getPlayer().playerState, 0, 10);
        spriteBatch.end();

    }

    @Override
    public void showImplement() {
        uiStage.clear();
        uiStage.addActor(uiTable);
        Gdx.input.setInputProcessor(uiStage);
        renderer = new ImageRenderer(gameControl);
        window.getTitleLabel().setText(gameControl.getLevel().image);
    }

    @Override
    public void resize(int width, int height) {
        this.width = 20f;
        this.height = 20f * height / width;
    }
}
