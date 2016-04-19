package com.upright.ldthreefive.logic;

import com.upright.ldthreefive.logic.builder.GameDefinition;
import com.upright.ldthreefive.logic.builder.LevelBuilder;
import com.upright.ldthreefive.logic.builder.LevelDefinition;
import com.upright.ldthreefive.logic.levelobjects.player.Player;
import com.upright.ldthreefive.logic.levelobjects.player.Upgrade;

/**
 * Created by Stygian on 4/18/2016.
 */
public class Game {
    private GameDefinition gameDefinition;
    private LevelDefinition levelDefinition;
    private Level level;
    private int essenceLevel = 0;
    private boolean displayedIntro = false;

    public Game(GameDefinition gameDefinition) {
        this.gameDefinition = gameDefinition;
    }

    public void initialize() {
        for (Upgrade upgrade : Upgrade.values()) {
            upgrade.setLevel(0);
        }
        displayedIntro = false;
    }

    public void loadLevel(int levelIndex) {
        levelDefinition = gameDefinition.levelDefinitionArray.get(levelIndex);
        loadLevel();
    }

    public void loadLevel() {
        LevelBuilder levelBuilder = new LevelBuilder();
        Player player = new Player();
        level = levelBuilder.buildLevel(player, levelDefinition);
        player.computeStats();
    }

    public void finishLevel() {
        essenceLevel += level.player.essenseLevel;
    }

    public GameDefinition getGameDefinition() {
        return gameDefinition;
    }

    public Level getLevel() {
        return level;
    }

    public int getEssenceLevel() {
        return essenceLevel;
    }

    public void setEssenceLevel(int essenceLevel) {
        this.essenceLevel = essenceLevel;
    }

    public boolean isDisplayedIntro() {
        return displayedIntro;
    }

    public void setDisplayedIntro(boolean displayedIntro) {
        this.displayedIntro = displayedIntro;
    }
}
