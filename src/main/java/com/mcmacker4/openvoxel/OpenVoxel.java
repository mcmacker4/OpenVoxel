package com.mcmacker4.openvoxel;

import com.mcmacker4.openvoxel.graphics.Camera;
import com.mcmacker4.openvoxel.graphics.WorldRenderer;
import com.mcmacker4.openvoxel.shaders.WorldShader;
import com.mcmacker4.openvoxel.texture.Texture;
import com.mcmacker4.openvoxel.util.Timer;
import com.mcmacker4.openvoxel.world.World;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by McMacker4 on 05/08/2016.
 */
class OpenVoxel {

    WorldShader worldShader;
    WorldRenderer renderer;
    World world;

    Camera camera;

    void start() {
        Display.create(1280, 720, "OpenVoxel Project");
        init();
        while(!Display.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            update();
            render();
            Display.update();
        }
        destroy();
    }

    private void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.3f, 0.6f, 0.9f, 1.0f);
        Texture.loadTextures();
        worldShader = new WorldShader();
        camera = new Camera(90f);
        world = new World(camera);
        renderer = new WorldRenderer(world, worldShader);
    }

    private void update() {
        Timer.update();
        camera.update();
        world.update();
    }

    private void render() {
        renderer.render();
    }

    private void destroy() {
        world.delete();
        Texture.destroy();
        Display.destroy();
    }

}
