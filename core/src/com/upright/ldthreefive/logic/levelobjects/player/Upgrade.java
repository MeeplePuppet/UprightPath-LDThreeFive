package com.upright.ldthreefive.logic.levelobjects.player;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Stygian on 4/16/2016.
 */
public enum Upgrade {
    HEALTH("Health", "Increases health per point (Sneak: 1, Hide: 2, Eat and Human: 4.)", 4, 10),
    HIDE_DAMAGE("Hide A. Damage", "Increases Hide attack damage by 2 per point.", 5, 20),
    HIDE_COOLDOWN("Hide A. Speed", "Increases Hide attack speed (0: 1.5s, 1: 1s, 2: .5s.)", 2, 10),
    HIDE_TRANSFORM("Hide T. Speed", "Increases Hide transform speed (0:3s, 1: 2s, 2: 1s.)", 2, 10),
    SNEAK_TRANSFORM("Sneak T. Speed", "Increases Sneak transform speed (0:3s, 1: 2s, 2: 1s.)", 2, 5),
    HUMAN_DAMAGE("Human A. Damage", "Increases Hide attack damage by 1 per point.", 5, 25),
    HUMAN_COOLDOWN("Human A. Speed", "Increases Hide attack speed (0: 3s, 1: 2s, 2: 1s.)", 2, 25),
    HUMAN_TRANSFORM("Human T. Speed", "Increases Hide transform speed (0:6s, 1: 4s, 2: 2s.)", 2, 10),
    EATING_DAMAGE("Eating A. Damage", "Increases Hide attack damage by 1 per point.", 5, 15),
    EATING_COOLDOWN("Eating A. Speed", "Increases Hide attack speed (0: 1.5s, 1: 1s, 2:.5s.)", 2, 15),
    EATING_TRANSFORM("Eating T. Speed", "Increases Hide transform speed (0:3s, 1: 2s, 2: 1s.)", 2, 10);

    private String name;
    private String description;
    private int maxLevel;
    private int level;
    private int essenceCost;

    Upgrade(String name, String description, int maxLevel, int essenceCost) {
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.essenceCost = essenceCost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int costToUpgrade() {
        return level < maxLevel ? essenceCost * (level + 1) : -1;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = MathUtils.clamp(level, 0, maxLevel);
    }
}
