package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.shaders.ShaderProgram;
import com.mcmacker4.openvoxel.shaders.WorldShader;
import com.mcmacker4.openvoxel.texture.Texture;
import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class WorldRenderer {

    private World world;
    private WorldShader shader;
    private Camera camera;

    public WorldRenderer(World world, WorldShader shader, Camera camera) {
        this.world = world;
        this.shader = shader;
        this.camera = camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void render() {
        shader.start();
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        shader.loadViewMatrix(camera.getViewMatrix());
        shader.loadLightDir(world.getLightDir());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, Texture.TERRAIN);
        world.getChunks().forEach(chunk -> {
            Vector3f pos = new Vector3f(
                    chunk.getChunkPosition().x * Chunk.SIZE_X,
                    0,
                    chunk.getChunkPosition().y * Chunk.SIZE_Z
            );
            shader.loadModelMatrix(new Matrix4f().translate(pos));
            BakedChunk baked = chunk.getBakedChunk();
            if(baked != null) {
                VertexBuffer buffer = baked.getVbo();
                buffer.bind();
                glEnableVertexAttribArray(0);
                glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);
                glEnableVertexAttribArray(1);
                glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * 4, 3 * 4);
                glEnableVertexAttribArray(2);
                glVertexAttribPointer(2, 3, GL_FLOAT, false, 8 * 4, 5 * 4);
                buffer.draw();
                buffer.unbind();
                glDisableVertexAttribArray(2);
                glDisableVertexAttribArray(1);
                glDisableVertexAttribArray(0);
            }
        });
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindVertexArray(0);
        ShaderProgram.stop();
    }

}
