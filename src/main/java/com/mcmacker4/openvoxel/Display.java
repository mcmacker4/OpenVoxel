package com.mcmacker4.openvoxel;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Display {

    private static long window;
    private static long inv_window;

    private static int WIDTH, HEIGHT;

    static void create(int width, int height, String title) {

        WIDTH = width;
        HEIGHT = height;

        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Could not initialize GLFW.");

        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if(window == 0)
            throw new IllegalStateException("Could not create GLFW Window.");

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        inv_window = glfwCreateWindow(800, 600, "Invisible", NULL, window);
        if(inv_window == 0)
            throw new IllegalStateException("Could not create GLFW context for worker thread.");

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        glfwSwapInterval(0);

        System.out.println(glGetString(GL_VERSION));

        glfwShowWindow(window);

    }

    public static float aspectRatio() {
        return (float) WIDTH / HEIGHT;
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public static void setTitle(String title) {
        glfwSetWindowTitle(window, title);
    }

    static void update() {
        glfwPollEvents();
        Input.update();
        glFlush();
        glfwSwapBuffers(window);
    }

    static void destroy() {
        glfwDestroyWindow(inv_window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static long getWindow() {
        return window;
    }

    public static long getWorkerWindow() {
        return inv_window;
    }

}
