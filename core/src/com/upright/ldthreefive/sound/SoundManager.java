package com.upright.ldthreefive.sound;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.upright.ldthreefive.LDThreeFive;

import java.util.HashMap;

/**
 * Created by Stygian on 4/15/2016.
 */
public class SoundManager {
    /**
     * The main game.
     */
    public LDThreeFive mainGame;

    /**
     * Whether the sound is mute.
     */
    private boolean soundMute = false;

    /**
     * Getter for sound mute.
     *
     * @return True if sound is mute.
     */
    public boolean isSoundMute() {
        return soundMute;
    }

    /**
     * Setter for sound mute.
     * @param soudMute Whether the sound is mute.
     */
    public void setSoundMute(boolean soudMute)
    {
        this.soundMute = soundMute;
    }

    /**
     * The sound level.
     */
    private float soundVolume = .5f;

    /**
     * Whether the music is mute.
     */
    private boolean musicMute = false;

    /**
     * Getter for music mute.
     * @return Whether the music is mute.
     */
    public boolean isMusicMute()
    {
        return musicMute;
    }

    /**
     * Setter for music mute.
     * @param musicMute
     */
    public void setMusicMute(boolean musicMute)
    {
        this.musicMute = musicMute;
        updateMusicVolume();
    }

    /**
     * The music level.
     */
    private float musicVolume = .5f;

    /**
     * Getter for the music volume.
     * @return The music volume.
     */
    public float getMusicVolume()
    {
        return musicVolume;
    }

    /**
     * Setter for the music volume.
     * @param volume The music volume.
     */
    public void setMusicVolume(float volume)
    {
        musicVolume = MathUtils.clamp(volume, 0, 1);
        updateMusicVolume();
    }

    /**
     * Updates the music volume.
     */
    public void updateMusicVolume()
    {
        for (Music music : loadedMusic.values())
        {
            music.setVolume(musicMute ? 0 : musicVolume);
        }
    }

    /**
     * Sounds.
     */
    private HashMap<String, Sound> loadedSound = new HashMap<String, Sound>();

    /**
     * Music
     */
    private HashMap<String, Music> loadedMusic = new HashMap<String, Music>();

    /**
     * Plays the specified sound.
     *
     * @param soundName The name of the sound.
     */
    public void playSound(String soundName) {
        Sound sound = loadedSound.get(soundName);
        if (sound == null) {
            sound = mainGame.gameAssets.get(soundName + ".mid", Sound.class);
            loadedSound.put(soundName, sound);
        }
        sound.play(soundMute ? 0f : soundVolume);
    }

    /**
     * Plays the specified sound.
     *
     * @param musicName The name of the music.
     */
    public void playMusic(String musicName) {
        Music music = loadedMusic.get(musicName);
        if (music == null) {
            music = mainGame.gameAssets.get(musicName + ".mid", Music.class);
            loadedMusic.put(musicName, music);
        }
        music.setVolume(musicMute ? 0f : musicVolume);
        music.play();
        music.setLooping(true);
    }

    /**
     * Stops a specific piece of music.
     *
     * @param musicName The music to stop.
     */
    public void stopMusic(String musicName) {
        Music music = loadedMusic.get(musicName);
        if (music != null) {
            music.stop();
        }
    }

    /**
     * Stops all music currently playing.
     */
    public void stopMusic() {
        for (Music music : loadedMusic.values()) {
            music.setLooping(false);
            music.stop();
        }
    }
}
