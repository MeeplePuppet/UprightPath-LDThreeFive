package com.upright.ldthreefive.logic.levelobjects;

/**
 * Created by Stygian on 4/15/2016.
 */
public class Event extends InteractableLevelObject {
    public EventType eventType;
    public float distanceSquare;

    public Event(EventType eventType, float distanceSquare, int health) {
        this.eventType = eventType;
        this.distanceSquare = distanceSquare;
        this.health = health;
    }

    public void update() {
        health--;
    }

    @Override
    public boolean blocksVision() {
        return false;
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
