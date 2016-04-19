package com.upright.ldthreefive.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.logic.levelobjects.*;
import com.upright.ldthreefive.logic.levelobjects.agents.Agent;
import com.upright.ldthreefive.logic.levelobjects.player.Player;
import com.upright.ldthreefive.logic.wincondition.WinCondition;

/**
 * Created by Stygian on 4/15/2016.
 */
public class Level {
    BodyDef staticBodyDef = new BodyDef();

    public float offsetX = 0;
    public float offsetY = 0;

    public String image;

    public String description = "";

    public World world;

    public Player player;

    public WinCondition winCondition;

    public Array<LevelObject> collisions = new Array<LevelObject>();
    private Array<LevelObject> collisionsSwap = new Array<LevelObject>();

    public Array<MobileLevelObject> mobiles = new Array<MobileLevelObject>();
    private Array<MobileLevelObject> mobilesSwap = new Array<MobileLevelObject>();

    public Array<Event> events = new Array<Event>();
    private Array<Event> eventsSwap = new Array<Event>();

    public Level(float sizeX, float sizeY) {
        world = new World(new Vector2(sizeX, sizeY), true);
        staticBodyDef.type = BodyDef.BodyType.StaticBody;
    }

    public Array<PathNode> pathNodes = new Array<PathNode>();

    public void updateForLineOfSight(boolean lineOfSight) {
        for (int i = 0; i < collisions.size; i++) {
            if (lineOfSight) {
                collisions.get(i).body.setActive(collisions.get(i).blocksVision());
            } else {
                collisions.get(i).body.setActive(collisions.get(i).blocksMovement());
            }
        }
    }

    public void cleanUp() {
        Array<LevelObject> collisionsHolder = collisions;
        Array<MobileLevelObject> mobilesHolder = mobiles;
        Array<Event> eventsHolder = events;
        collisions = collisionsSwap;
        mobiles = mobilesSwap;
        events = eventsSwap;
        collisionsSwap = collisionsHolder;
        mobilesSwap = mobilesHolder;
        eventsSwap = eventsHolder;
        collisions.clear();
        mobiles.clear();
        events.clear();
        for (LevelObject obj : collisionsSwap) {
            if (!obj.isDestroyed()) {
                collisions.add(obj);
            } else {
                world.destroyBody(obj.body);
            }
        }
        for (MobileLevelObject obj : mobilesSwap) {
            if (!obj.isDestroyed()) {
                mobiles.add(obj);
            } else {
                world.destroyBody(obj.body);
            }
        }
        for (Event obj : eventsSwap) {
            if (!obj.isDestroyed()) {
                events.add(obj);
            } else {
                world.destroyBody(obj.body);
            }
            obj.update();
        }
    }

    public Event getNearestEvent(Vector2 curLoc) {
        Event closestEvent = null;
        float closestDist = Float.MAX_VALUE;
        for (Event event : events) {
            if (event.eventType != EventType.ENEMY_NOISE) {
                float dist = event.getTargetLocation().dst2(curLoc);
                if (dist < event.distanceSquare && dist < closestDist) {
                    closestEvent = event;
                    closestDist = dist;
                }
            }
        }
        return closestEvent;
    }

    public boolean playerIsTargeted() {
        for (int i = 0; i < mobiles.size; i++) {
            if (mobiles.get(i) instanceof Agent) {
                if (((Agent) mobiles.get(i)).target == player) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addEvent(Event event, Vector2 position) {
        staticBodyDef.position.set(position);
        Body body = world.createBody(staticBodyDef);
        body.setFixedRotation(true);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius((float) Math.sqrt(event.distanceSquare));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
        event.body = body;
        event.body.setTransform(position.x, position.y, 0);
        event.body.setUserData(event);
        event.body.setActive(false);
        events.add(event);
    }

    public void update() {
        updateForLineOfSight(true);
        player.decideAction(this);
        for (MobileLevelObject mobileLevelObject : mobiles) {
            mobileLevelObject.decideAction(this);
        }
        updateForLineOfSight(false);
        player.performAction(this);
        for (MobileLevelObject mobileLevelObject : mobiles) {
            mobileLevelObject.performAction(this);
        }
        player.updatePositions();
        cleanUp();
    }

    public boolean gameLost() {
        return player.isDestroyed();
    }

    public boolean gameWon() {
        return winCondition.hasWon(this);
    }
}
