package com.upright.ldthreefive.logic.builder;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.upright.ldthreefive.logic.Level;
import com.upright.ldthreefive.logic.levelobjects.Door;
import com.upright.ldthreefive.logic.levelobjects.PathNode;
import com.upright.ldthreefive.logic.levelobjects.Wall;
import com.upright.ldthreefive.logic.levelobjects.agents.Agent;
import com.upright.ldthreefive.logic.levelobjects.agents.Patroller;
import com.upright.ldthreefive.logic.levelobjects.agents.Rotating;
import com.upright.ldthreefive.logic.levelobjects.agents.Stationed;
import com.upright.ldthreefive.logic.levelobjects.player.Player;

/**
 * Created by Stygian on 4/15/2016.
 */
public class LevelBuilder {
    BodyDef staticBodyDef;
    BodyDef kinematicBodyDef;
    BodyDef dynamicBodyDef;


    public LevelBuilder() {
        staticBodyDef = new BodyDef();
        staticBodyDef.type = BodyDef.BodyType.StaticBody;
        kinematicBodyDef = new BodyDef();
        kinematicBodyDef.type = BodyDef.BodyType.StaticBody;
        dynamicBodyDef = new BodyDef();
        dynamicBodyDef.type = BodyDef.BodyType.DynamicBody;
    }

    public Level buildLevel(Player player, LevelDefinition levelDefinition) {
        Level level = new Level(levelDefinition.sizeX, levelDefinition.sizeY);
        level.image = levelDefinition.levelLayoutImage;
        level.description = levelDefinition.description;
        level.world.setGravity(new Vector2(0, 0));
        level.winCondition = levelDefinition.winCondition;
        level.offsetX = levelDefinition.offsetX;
        level.offsetY = levelDefinition.offsetY;
        addPlayer(level, levelDefinition.playerDefinition);
        for (Polygon polygon : levelDefinition.walls) {
            addWall(level, polygon);
        }
        for (Polygon polygon : levelDefinition.doors) {
            addDoor(level, polygon);
        }
        for (PathNodeDefinition pathNodeDefinition : levelDefinition.pathNodes) {
            addPathNode(level, pathNodeDefinition);
        }
        for (int i = 0; i < levelDefinition.pathNodes.size; i++) {
            PathNode pathNode = level.pathNodes.get(i);
            for (Integer pathNodeIndex : levelDefinition.pathNodes.get(i).adjacentNodes) {
                pathNode.adjacentNodes.add(level.pathNodes.get(pathNodeIndex));
            }
        }
        for (AgentDefinition agent : levelDefinition.agents) {
            if (agent instanceof PatrollerDefinition) {
                addPatroller(level, (PatrollerDefinition) agent);
            } else if (agent instanceof RotatingDefinition) {
                addRotating(level, (RotatingDefinition) agent);
            } else if (agent instanceof StationedDefinition) {
                addStationed(level, (StationedDefinition) agent);
            } else {

            }
        }
        return level;
    }

    public void addWall(Level level, Polygon polygon) {
        Body body = level.world.createBody(staticBodyDef);

        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(rewrapVertices(polygon.getVertices()));
        body.createFixture(chainShape, 0.0f);
        chainShape.dispose();

        Wall wall = new Wall();
        wall.body = body;
        body.setUserData(wall);
        level.collisions.add(wall);
    }

    public void addDoor(Level level, Polygon polygon) {
        Body body = level.world.createBody(staticBodyDef);
        body.setActive(false);

        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(rewrapVertices(polygon.getVertices()));
        body.createFixture(chainShape, 0.0f);
        chainShape.dispose();

        Door door = new Door();
        door.body = body;
        body.setUserData(door);
        level.collisions.add(door);
    }

    public void addPathNode(Level level, PathNodeDefinition pathNodeDefinition) {
        Body body = level.world.createBody(staticBodyDef);
        body.setActive(false);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.3f);
        body.createFixture(circleShape, 0.0f);
        circleShape.dispose();


        PathNode pathNode = new PathNode();
        pathNode.position = pathNodeDefinition.position;
        level.pathNodes.add(pathNode);
        pathNode.body = body;
        pathNode.body.setTransform(pathNodeDefinition.position.x, pathNodeDefinition.position.y, 0);
        body.setUserData(pathNode);
    }

    public void addPlayer(Level level, PlayerDefinition playerDefinition) {
        Player player = new Player();
        level.player = player;
        dynamicBodyDef.position.set(playerDefinition.position);

        // Sneak Body
        Body body = level.world.createBody(dynamicBodyDef);
        body.setSleepingAllowed(false);
        body.setFixedRotation(true);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = .1f;
        fixtureDef.restitution = .1f;
        body.createFixture(fixtureDef);
        player.sneakBody = body;
        player.sneakBody.setTransform(playerDefinition.position.x, playerDefinition.position.y, playerDefinition.rotation);
        player.sneakBody.setUserData(player);

        // Big Body
        body = level.world.createBody(dynamicBodyDef);
        body.setSleepingAllowed(false);
        body.setFixedRotation(true);
        circleShape.setRadius(.3f);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = .1f;
        fixtureDef.restitution = .1f;
        body.createFixture(fixtureDef);
        player.bigBody = body;
        player.bigBody.setTransform(playerDefinition.position.x, playerDefinition.position.y, playerDefinition.rotation);
        player.bigBody.setUserData(player);

        // Hide Body
        body = level.world.createBody(dynamicBodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(.25f, .25f);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 10f;
        fixtureDef.friction = .9f;
        fixtureDef.restitution = .1f;
        body.createFixture(fixtureDef);
        player.hideBody = body;
        player.hideBody.setTransform(playerDefinition.position.x, playerDefinition.position.y, playerDefinition.rotation);
        player.hideBody.setUserData(player);
        player.updatePlayerType();
    }

    public void addPatroller(Level level, PatrollerDefinition patrollerDefinition) {
        Patroller patroller = new Patroller();
        for (Integer pathNodeIndex : patrollerDefinition.pathNode) {
            patroller.pathNodeArray.add(level.pathNodes.get(pathNodeIndex));
        }
        patroller.loop = patrollerDefinition.loop;

        addAgent(level, patrollerDefinition, patroller, dynamicBodyDef);
    }

    public void addRotating(Level level, RotatingDefinition rotatingDefinition) {
        Rotating rotating = new Rotating();
        rotating.minRotation = rotatingDefinition.minRotation;
        rotating.maxRotation = rotatingDefinition.maxRotation;

        addAgent(level, rotatingDefinition, rotating, kinematicBodyDef);
    }

    public void addStationed(Level level, StationedDefinition rotatingDefinition) {
        Stationed stationed = new Stationed();

        addAgent(level, rotatingDefinition, stationed, kinematicBodyDef);
    }

    public void addAgent(Level level, AgentDefinition agentDefinition, Agent agent, BodyDef bodyType) {
        dynamicBodyDef.position.set(agentDefinition.position);

        agent.health = agentDefinition.health;
        agent.turnSpeed = agentDefinition.turnSpeed;
        agent.attackCoolDown = agentDefinition.attackCoolDown;

        Body body = level.world.createBody(bodyType);
        body.setSleepingAllowed(false);
        body.setFixedRotation(true);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.3f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = .1f;
        body.createFixture(fixtureDef);
        agent.body = body;
        agent.body.setTransform(agentDefinition.position.x, agentDefinition.position.y, agentDefinition.rotation);
        agent.body.setUserData(agent);
        agent.body.setActive(true);
        level.mobiles.add(agent);
    }

    public float[] rewrapVertices(float[] verticies) {
        float[] rewrapped = new float[verticies.length];
        for (int i = 0, j = verticies.length - 2; i < verticies.length; i += 2, j -= 2) {
            rewrapped[i] = verticies[j] - 1;
            rewrapped[i + 1] = verticies[j + 1] - 1;
        }
        return rewrapped;
    }
}
