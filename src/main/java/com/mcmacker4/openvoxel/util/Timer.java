package com.mcmacker4.openvoxel.util;

import com.mcmacker4.openvoxel.Display;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Created by McMacker4 on 07/08/2016.
 */
public class Timer {

    static double last;
    static double lastFPS;
    static double delta;

    static int frameCount;
    static int FPS;

    public static void update() {
        double now = glfwGetTime();
        delta = now - last;
        last = now;
        if(now - lastFPS > 1) {
            lastFPS = now;
            FPS = frameCount;
            frameCount = 0;
            Display.setTitle("FPS: " + FPS);
        }
        frameCount++;
    }

    public static double getDelta() {
        return delta;
    }
}
