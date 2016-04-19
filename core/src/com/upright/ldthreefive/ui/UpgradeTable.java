package com.upright.ldthreefive.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.upright.ldthreefive.GameControl;
import com.upright.ldthreefive.logic.levelobjects.player.Upgrade;

/**
 * Created by Stygian on 4/18/2016.
 */
public class UpgradeTable extends Table {
    private GameControl gameControl;
    private TextButton[] buyButtons = new TextButton[Upgrade.values().length];
    private Label[] buyLabels = new Label[Upgrade.values().length];
    private TextArea descriptionArea;


    public UpgradeTable(final GameControl gameControl) {
        this.gameControl = gameControl;
        descriptionArea = new TextArea("", gameControl.getSkin());
        for (final Upgrade upgrade : Upgrade.values()) {
            ChangeListener changeListener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (gameControl.getGame().getEssenceLevel() >= upgrade.costToUpgrade()) {
                        gameControl.getGame().setEssenceLevel(gameControl.getGame().getEssenceLevel() - upgrade.costToUpgrade());
                        upgrade.setLevel(upgrade.getLevel() + 1);
                    }
                    update();
                }
            };
            InputListener inputListener = new InputListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    descriptionArea.setText(upgrade.getDescription());
                }
            };
            buyButtons[upgrade.ordinal()] = new TextButton("", gameControl.getSkin());
            buyButtons[upgrade.ordinal()].addListener(changeListener);
            buyButtons[upgrade.ordinal()].addListener(inputListener);
            buyLabels[upgrade.ordinal()] = new Label("", gameControl.getSkin());
            buyLabels[upgrade.ordinal()].addListener(inputListener);
            add(buyLabels[upgrade.ordinal()]);
            add(buyButtons[upgrade.ordinal()]);
            if (upgrade.ordinal() % 2 != 0) {
                row();
            }
        }
        row();
        add(descriptionArea).height(50f).fillX().colspan(4);
    }

    public void update() {
        for (Upgrade upgrade : Upgrade.values()) {
            if (upgrade.costToUpgrade() > 0) {
                buyButtons[upgrade.ordinal()].setText(upgrade.costToUpgrade() + "");
                buyButtons[upgrade.ordinal()].setDisabled(upgrade.costToUpgrade() > gameControl.getGame().getEssenceLevel());
            } else {
                buyButtons[upgrade.ordinal()].setText("MAXED");
                buyButtons[upgrade.ordinal()].setDisabled(true);
            }
            buyLabels[upgrade.ordinal()].setText(upgrade.getName() + "(" + upgrade.getLevel() + "/" + upgrade.getMaxLevel() + ")");
        }
    }
}
