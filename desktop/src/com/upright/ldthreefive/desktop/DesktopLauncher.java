package com.upright.ldthreefive.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Json;
import com.upright.ldthreefive.LDThreeFive;
import com.upright.ldthreefive.logic.builder.*;

import java.io.File;

public class DesktopLauncher {
    public static void main(String[] arg) {
        //TexturePacker.process("C:\\Programming\\UprightPath-LDThreeFive\\Assets\\game", "C:\\Programming\\UprightPath-LDThreeFive\\android\\assets", "game-assets");
        //TexturePacker.process("C:\\Programming\\UprightPath-LDThreeFive\\Assets\\load", "C:\\Programming\\UprightPath-LDThreeFive\\android\\assets", "load-assets");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        new LwjglApplication(new LDThreeFive(), config);
    }
}
