package com.upright.ldthreefive.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.upright.ldthreefive.control.ControlAction;

/**
 * Created by Stygian on 4/15/2016.
 */
public class DirectionControl extends Table {
    /**
     * The instance.
     */
    public static DirectionControl instance;

    /**
     * Initializes the control.
     * @param skin The skin.
     */
    public static void initialize(Skin skin)
    {
        instance = new DirectionControl(skin);
    }

    /**
     * The up button.
     */
    public ImageButton upButton;

    /**
     * The down button.
     */
    public ImageButton downButton;

    /**
     * The left button.
     */
    public ImageButton leftButton;

    /**
     * The right button.
     */
    public ImageButton rightButton;

    private DirectionControl(Skin skin)
    {
        upButton = new ImageButton(skin, "upButton");
        downButton = new ImageButton(skin, "downButton");
        leftButton = new ImageButton(skin, "leftButton");
        rightButton = new ImageButton(skin, "rightButton");
        this.add();
        this.addActor(upButton);
        this.row();
        this.addActor(leftButton);
        this.add();
        this.addActor(rightButton);
        this.row();
        this.add();
        this.addActor(downButton);
        this.row();
        ControlAction.UP.button = upButton;
        ControlAction.DOWN.button = downButton;
        ControlAction.LEFT.button = leftButton;
        ControlAction.RIGHT.button = rightButton;
    }
}
