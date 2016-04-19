package com.upright.ldthreefive.logic.levelobjects.player;

/**
 * Created by Stygian on 4/16/2016.
 */
public enum PlayerType {
    EATING("Eating"),
    SNEAK("Sneak"),
    HIDE("Hide"),
    HUMAN("Human");

    private String name;

    PlayerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
