package com.upright.ldthreefive.ui.render.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.upright.ldthreefive.GameControl;
import com.upright.ldthreefive.logic.levelobjects.player.Player;

/**
 * Created by Stygian on 4/18/2016.
 */
public class PlayerRenderer extends MobSpriteProvider<Player> {

    private GameControl gameControl;
    private MobSpriteProvider<Player> transformRenderer;
    private MobSpriteProvider<Player> eatingRenderer;
    private MobSpriteProvider<Player> sneakRenderer;
    private MobSpriteProvider<Player> hideRenderer;
    private MobSpriteProvider<Player> humanRenderer;

    public PlayerRenderer(GameControl gameControl) {
        super(gameControl, "Player");
        transformRenderer = new MobSpriteProvider<Player>(gameControl, "Transforming");
        eatingRenderer = new MobSpriteProvider<Player>(gameControl, "Eating");
        sneakRenderer = new MobSpriteProvider<Player>(gameControl, "Sneak");
        hideRenderer = new MobSpriteProvider<Player>(gameControl, "Hide");
        humanRenderer = new MobSpriteProvider<Player>(gameControl, "Human");

    }

    public Sprite getSprite(Player player) {
        MobSpriteProvider<Player> renderer;
        switch (player.playerState) {
            case TRANSFORMING: {
                renderer = transformRenderer;
                break;
            }
            default: {
                switch (player.playerType) {
                    case EATING: {
                        renderer = eatingRenderer;
                        break;
                    }
                    case HIDE: {
                        renderer = hideRenderer;
                        break;
                    }
                    case HUMAN: {
                        renderer = humanRenderer;
                        break;
                    }
                    default: {
                        renderer = sneakRenderer;
                        break;
                    }
                }
                break;
            }
        }
        return renderer.getSprite(player);
    }
}
