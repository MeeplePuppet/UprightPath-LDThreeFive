package com.upright.ldthreefive.logic.wincondition;

import com.badlogic.gdx.math.Vector2;
import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/18/2016.
 */
public class GetToPosition implements WinCondition {
    private Vector2 position;

    public GetToPosition() {}

    public GetToPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public boolean hasWon(Level level) {
        return level.player.getTargetLocation().dst2(position) < 1f;
    }

    @Override
    public String getWinDescription() {
        return "You arrived.";
    }

    @Override
    public String getDescription() {
        return "Get to the goal (red square.)";
    }
}
