package com.upright.ldthreefive;

/**
 * Created by Stygian on 4/18/2016.
 */
public class Options {
    public boolean debug;
    public float musicVolume;
    public boolean musicMute;
    public float soundVolume;
    public boolean soundMute;
    public int[] keyCode;

    public void load(GameControl gameControl) {
        gameControl.setDebug(debug);
        gameControl.getSoundManager().setMusicMute(musicMute);
        gameControl.getSoundManager().setMusicVolume(musicVolume);
        gameControl.getSoundManager().setSoundMute(soundMute);
        gameControl.getSoundManager().setSoundVolume(soundVolume);
        for (ControlAction controlAction : ControlAction.values()) {
            controlAction.setKeyCode(keyCode[controlAction.ordinal()]);
        }
    }

    public void save(GameControl gameControl) {
        debug = gameControl.isDebug();
        musicMute = gameControl.getSoundManager().isMusicMute();
        musicVolume = gameControl.getSoundManager().getMusicVolume();
        soundMute = gameControl.getSoundManager().isSoundMute();
        soundVolume = gameControl.getSoundManager().getSoundVolume();
        keyCode = new int[ControlAction.values().length];
        for (ControlAction controlAction : ControlAction.values()) {
            keyCode[controlAction.ordinal()] = controlAction.getKeyCode();
        }
    }
}
