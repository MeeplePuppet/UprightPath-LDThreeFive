package com.upright.ldthreefive.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.GameControl;

/**
 * Loading screen; runs while the game assets are being loaded.
 */
public class LoadScreen extends GameScreen {
    SpriteBatch spriteBatch = new SpriteBatch();
    TextureAtlas textureAtlas;
    Array<Sprite> loadingSprites;
    int tick = 0;

    /**
     * Constructor.
     *
     * @param gameControl The main game object.
     */
    public LoadScreen(GameControl gameControl) {
        super(gameControl);
    }

    @Override
    public void showImplement() {
        loadingSprites = gameControl.getGraphicsManager().getAnimation("Loading");
        gameControl.startGettingGameAssets();
        tickSpeed = 1f / loadingSprites.size;
        gameControl.getSoundManager().playMusic("sounds/Static.wav");
    }

    @Override
    public void hide() {
        gameControl.getSoundManager().stopMusic("sounds/Static.wav");
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        for (Sprite sprite : loadingSprites) {
            sprite.setBounds(width / 2 - height / 2, 0, height, height);
        }
    }

    @Override
    public void update() {
        tick = (tick + 1) % loadingSprites.size;
        if (tick == 0 && gameControl.getGameAssetManager().update()) {
            gameControl.finishGettingGameAssets();
            gameControl.switchScreen(ScreenType.MAIN);
        }
    }

    @Override
    public void renderImplement(float delta) {
        spriteBatch.begin();
        loadingSprites.get(tick).draw(spriteBatch);
        spriteBatch.end();
    }
}
