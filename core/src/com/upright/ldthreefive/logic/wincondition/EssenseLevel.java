package com.upright.ldthreefive.logic.wincondition;

import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/18/2016.
 */
public class EssenseLevel implements WinCondition {
    private int essenceNeeded;

    public EssenseLevel() {
    }

    public EssenseLevel(int essenceNeeded) {
        this.essenceNeeded = essenceNeeded;
    }

    @Override
    public boolean hasWon(Level level) {
        if (level.player.essenseLevel >= essenceNeeded) {
            level.player.essenseLevel = essenceNeeded;
            return true;
        }
        return false;
    }

    @Override
    public String getWinDescription() {
        return "You've absorbed enough essence.";
    }

    @Override
    public String getDescription() {
        return "Absorb at least " + essenceNeeded + " essence.";
    }
}
