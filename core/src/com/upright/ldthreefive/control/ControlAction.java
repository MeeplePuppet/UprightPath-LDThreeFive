package com.upright.ldthreefive.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import javax.print.DocFlavor;

/**
 * Created by Stygian on 4/14/2016.
 */
public enum ControlAction {
    UP(Input.Keys.UP),
    DOWN(Input.Keys.DOWN),
    LEFT(Input.Keys.LEFT),
    RIGHT(Input.Keys.RIGHT),
    ACCEPT(Input.Keys.ENTER),
    CANCEL(Input.Keys.ESCAPE);

    /**
     * The key associated with this action.
     */
    public int keyCode;

    /**
     * The button associated with this action.
     */
    public Button button;

    /**
     * The first time the key was pressed.
     */
    public boolean triggered = false;

    /**
     * Whether this is the first tick where the update was triggered.
     */
    public boolean firstTrigger = false;

    /**
     * The key this action is triggered with.
     *
     * @param keyCode The key code.
     */
    ControlAction(int keyCode) {
        this.keyCode = keyCode;
    }


    /**
     * Updates the action.
     */
    public void update() {
        update((button != null ? button.isChecked() : false) || Gdx.input.isKeyPressed(keyCode));
    }

    /**
     * Updates the action.
     *
     * @param triggered Whether the action was triggered.
     */
    public void update(boolean triggered) {
        if (triggered && !firstTrigger) {
            firstTrigger = false;
        } else if (triggered) {
            this.triggered = firstTrigger = true;
        } else {
            this.triggered = firstTrigger = false;
        }
    }
}
