package com.upright.ldthreefive.ui.options;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.upright.ldthreefive.GameControl;

/**
 * Created by Stygian on 4/17/2016.
 */
public class SoundManagerTable extends Table {
    private GameControl gameControl;
    private Label musicLabel;
    private Label soundLabel;
    private Slider musicSlider;
    private Slider soundSlider;
    private CheckBox musicCheckBox;
    private CheckBox soundCheckBox;

    public SoundManagerTable(GameControl gameControl) {
        this.gameControl = gameControl;
        musicLabel = new Label("Music", gameControl.getSkin());
        musicSlider = new Slider(0f, 1f, .001f, false, gameControl.getSkin());
        musicCheckBox = new CheckBox("Mute?", gameControl.getSkin());

        soundLabel = new Label("Sound", gameControl.getSkin());
        soundSlider = new Slider(0f, 1f, .001f, false, gameControl.getSkin());
        soundCheckBox = new CheckBox("Mute?", gameControl.getSkin());

        add(musicLabel);
        add(musicSlider).fillX();
        add(musicCheckBox);
        row();
        add(soundLabel);
        add(soundSlider).fillX();
        add(soundCheckBox);

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManagerTable.this.updateState();
            }
        };
        musicSlider.addListener(changeListener);
        musicCheckBox.addListener(changeListener);
        soundSlider.addListener(changeListener);
        soundCheckBox.addListener(changeListener);
    }

    public void initializeState() {
        musicCheckBox.setChecked(gameControl.getSoundManager().isMusicMute());
        musicSlider.setDisabled(gameControl.getSoundManager().isMusicMute());
        musicSlider.setValue(gameControl.getSoundManager().getMusicVolume());
        soundCheckBox.setChecked(gameControl.getSoundManager().isSoundMute());
        soundSlider.setDisabled(gameControl.getSoundManager().isSoundMute());
        soundSlider.setValue(gameControl.getSoundManager().getSoundVolume());
    }

    public void updateState() {
        gameControl.getSoundManager().setMusicVolume(musicSlider.getValue());
        gameControl.getSoundManager().setMusicMute(musicCheckBox.isChecked());
        gameControl.getSoundManager().setSoundVolume(soundSlider.getValue());
        gameControl.getSoundManager().setSoundMute(soundCheckBox.isChecked());
        initializeState();
    }
}
