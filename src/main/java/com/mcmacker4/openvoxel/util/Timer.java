package com.mcmacker4.openvoxel.util;

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

    static {
        last = now();
    }

    public static void update() {
        double now = now();
        delta = now - last;
        if(now - lastFPS > 1) {
            lastFPS = now;
            FPS = frameCount;
            frameCount = 0;
            System.out.println("FPS: " + FPS);
        }
        frameCount++;
    }

    public static double now() {
        return glfwGetTime();
    }

}
