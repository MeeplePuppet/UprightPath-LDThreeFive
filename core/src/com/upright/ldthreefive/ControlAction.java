package com.upright.ldthreefive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * Created by Stygian on 4/14/2016.
 */
public enum ControlAction {
    UP("Up", Input.Keys.UP),
    DOWN("Down", Input.Keys.DOWN),
    LEFT("Left", Input.Keys.LEFT),
    RIGHT("Right", Input.Keys.RIGHT),
    SWAP_TRANS("Next Shape", Input.Keys.A),
    TRANSFORM("Shapeshift", Input.Keys.S),
    RUN("Run", Input.Keys.Z),
    ATTACK("Attack", Input.Keys.X),
    ACCEPT("Accept", Input.Keys.ENTER),
    CANCEL("Cancel/Menu", Input.Keys.ESCAPE);

    public static void updateKeys() {
        for (ControlAction action : ControlAction.values()) {
            action.update();
        }
    }

    private String name;

    public String getName() {
        return name;
    }

    private int keyCode;

    private Button button;

    private boolean triggered = false;

    private boolean firstTrigger = false;

    /**
     * The key this action is triggered with.
     *
     * @param keyCode The key code.
     */
    ControlAction(String name, int keyCode) {
        this.name = name;
        this.setKeyCode(keyCode);
    }


    /**
     * Updates the action.
     */
    public void update() {
        update((getButton() != null ? getButton().isChecked() : false) || Gdx.input.isKeyPressed(getKeyCode()));
    }

    /**
     * Updates the action.
     *
     * @param updateTriggered Whether the action was triggered.
     */
    public void update(boolean updateTriggered) {
        if (updateTriggered && !this.isTriggered()) {
            firstTrigger = (true);
            triggered = (true);
        } else if (updateTriggered) {
            triggered = true;
            firstTrigger = false;
        } else {
            this.triggered = false;
            firstTrigger = false;
        }
    }

    /**
     * The key associated with this action.
     */
    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * The button associated with this action.
     */
    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    /**
     * The first time the key was pressed.
     */
    public boolean isTriggered() {
        return triggered;
    }

    /**
     * Whether this is the first updateState where the updateState was triggered.
     */
    public boolean isFirstTrigger() {
        return firstTrigger;
    }
}
