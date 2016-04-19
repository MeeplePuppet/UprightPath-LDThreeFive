package com.upright.ldthreefive.logic.levelobjects.agents;

/**
 * Created by Stygian on 4/16/2016.
 */
public enum AgentState {
    NORMAL(10),
    ALERT(20),
    SCARED(15),
    TERRIFIED(5);

    public int awareness;

    AgentState(int awareness) {
        this.awareness = awareness;
    }
}
