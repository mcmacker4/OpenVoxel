package com.mcmacker4.openvoxel;

import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Input {

    private static double mx = 0, my = 0;
    private static double dx, dy;

    static DoubleBuffer xpos, ypos;

    static {
        xpos = MemoryUtil.memAllocDouble(1);
        ypos = MemoryUtil.memAllocDouble(1);
    }

    static void update() {
        xpos.clear();
        ypos.clear();
        glfwGetCursorPos(Display.getWindow(), xpos, ypos);
        double x = xpos.get();
        double y = ypos.get();
        dx = x - mx;
        dy = y - my;
        mx = x;
        my = y;
    }

    public static boolean isKeyDown(int key) {
        return glfwGetKey(Display.getWindow(), key) != GLFW_RELEASE;
    }

    public static boolean isMouseButtonDown(int button) {
        return glfwGetMouseButton(Display.getWindow(), button) == GLFW_PRESS;
    }

    public static double getMx() {
        return mx;
    }

    public static double getMy() {
        return my;
    }

    public static double getDx() {
        return dx;
    }

    public static double getDy() {
        return dy;
    }

}
