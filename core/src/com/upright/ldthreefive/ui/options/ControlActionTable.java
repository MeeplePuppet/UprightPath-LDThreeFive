package com.upright.ldthreefive.ui.options;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.ControlAction;
import com.upright.ldthreefive.GameControl;

/**
 * Created by Stygian on 4/17/2016.
 */
public class ControlActionTable extends Table {
    private GameControl gameControl;
    private Array<ControlActionDisplay> controlActionDisplayArray = new Array<ControlActionDisplay>();

    public ControlActionTable(GameControl gameControl) {
        this.gameControl = gameControl;
        for (ControlAction controlAction : ControlAction.values()) {
            ControlActionDisplay controlActionDisplay = new ControlActionDisplay(controlAction);
            controlActionDisplayArray.add(controlActionDisplay);
            add(controlActionDisplay.label);
            add(controlActionDisplay.textButton).fillX().row();
        }
    }

    public void updateState() {
        for (ControlActionDisplay controlActionDisplay : controlActionDisplayArray) {
            controlActionDisplay.updateState();
        }
    }

    private class ControlActionDisplay extends Table {
        Label label;
        TextButton textButton;
        ControlAction controlAction;
        Dialog dialog;

        ControlActionDisplay(final ControlAction controlAction) {
            this.controlAction = controlAction;
            label = new Label(controlAction.getName(), gameControl.getSkin());
            textButton = new TextButton(Input.Keys.toString(controlAction.getKeyCode()), gameControl.getSkin());
            add(label);
            add(textButton);
            dialog = new Dialog("Select Key", gameControl.getSkin());
            final InputListener inputListener = new InputListener() {
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    System.out.println("Pressed?");
                    controlAction.setKeyCode(keycode);
                    dialog.getContentTable().clear();
                    dialog.text(Input.Keys.toString(controlAction.getKeyCode()));
                    updateState();
                    return false;
                }
            };
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    dialog.show(gameControl.getStage());
                }
            });
            dialog.addListener(inputListener);
            dialog.text(Input.Keys.toString(controlAction.getKeyCode()));
            dialog.button("Done");
        }

        public void updateState() {
            textButton.setText(Input.Keys.toString(controlAction.getKeyCode()));
        }
    }
}
