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
                VertexBuffer[] buffers = baked.getBuffers();
                for(int i = 0; i < 3; i++) {
                    buffers[i].bind();
                    glVertexAttribPointer(i, buffers[i].getVertexSize(), GL_FLOAT, false, 0, 0);
                    glEnableVertexAttribArray(i);
                }
                glDrawArrays(GL_TRIANGLES, 0, buffers[0].getVertexCount());
                for(int i = 0; i < 3; i++) {
                    buffers[i].unbind();
                    glDisableVertexAttribArray(i);
                }
            }
        });
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindVertexArray(0);
        ShaderProgram.stop();
    }

}
