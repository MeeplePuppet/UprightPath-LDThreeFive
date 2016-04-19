package com.upright.ldthreefive.ui.options;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.upright.ldthreefive.GameControl;

/**
 * Created by Stygian on 4/17/2016.
 */
public class OptionWindow extends Table {
    private GameControl gameControl;
    private CheckBox debugCheckBox;
    private SoundManagerTable soundManagerTable;
    private ControlActionTable controlActionTable;

    public OptionWindow(GameControl gameControl) {
        super(gameControl.getSkin());
        this.gameControl = gameControl;

        soundManagerTable = new SoundManagerTable(gameControl);
        controlActionTable = new ControlActionTable(gameControl);

        debugCheckBox = new CheckBox("Debug Mode?", gameControl.getSkin());
        debugCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                OptionWindow.this.gameControl.setDebug(debugCheckBox.isChecked());
            }
        });

        add(soundManagerTable).row();
        add(controlActionTable).row();
        add(debugCheckBox);
    }

    public void updateState() {
        debugCheckBox.setChecked(gameControl.isDebug());
        soundManagerTable.initializeState();
        controlActionTable.updateState();
    }
}
