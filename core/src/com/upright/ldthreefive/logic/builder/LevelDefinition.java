package com.upright.ldthreefive.logic.builder;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.logic.wincondition.WinCondition;

/**
 * Created by Stygian on 4/15/2016.
 */
public class LevelDefinition {
    public String levelLayoutImage;
    public String levelName;
    public String description;
    public String music;
    public float offsetX = 0;
    public float offsetY = 0;
    public float posX = 1;
    public float posY = 1;
    public float sizeX;
    public float sizeY;
    public WinCondition winCondition;
    public PlayerDefinition playerDefinition;
    public Array<Polygon> walls = new Array<Polygon>();
    public Array<Polygon> doors = new Array<Polygon>();
    public Array<PathNodeDefinition> pathNodes = new Array<PathNodeDefinition>();
    public Array<Vector2> containers = new Array<Vector2>();
    public Array<AgentDefinition> agents = new Array<AgentDefinition>();
}
