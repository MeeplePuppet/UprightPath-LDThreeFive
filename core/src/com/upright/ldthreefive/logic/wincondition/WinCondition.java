package com.upright.ldthreefive.logic.wincondition;

import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/18/2016.
 */
public interface WinCondition {
    boolean hasWon(Level level);

    String getDescription();

    String getWinDescription();
}
