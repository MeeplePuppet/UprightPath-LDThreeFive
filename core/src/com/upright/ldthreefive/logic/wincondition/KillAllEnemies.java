package com.upright.ldthreefive.logic.wincondition;

import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.MobileLevelObject;
import com.upright.ldthreefive.logic.levelobjects.agents.Agent;

/**
 * Created by Stygian on 4/18/2016.
 */
public class KillAllEnemies implements WinCondition {
    @Override
    public boolean hasWon(Level level) {
        for (MobileLevelObject mob : level.mobiles) {
            if (mob instanceof Agent) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getWinDescription() {
        return "All enemies have been killed.";
    }

    @Override
    public String getDescription() {
        return "Kill all enemies!";


    }
}
