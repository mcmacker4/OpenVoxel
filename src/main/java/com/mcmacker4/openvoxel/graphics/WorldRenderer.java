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
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class WorldRenderer {

    private World world;
    private WorldShader shader;

    public WorldRenderer(World world, WorldShader shader) {
        this.world = world;
        this.shader = shader;
    }

    public void render() {
        shader.start();
        Camera camera = world.getActiveCamera();
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        shader.loadViewMatrix(camera.getViewMatrix());
        shader.loadLightDir(world.getLightDir());
        synchronized (world.getChunks()) {
            world.getChunks().forEach(chunk -> {
                Vector3f pos = new Vector3f(
                        chunk.getChunkPosition().x * Chunk.SIZE_X,
                        0,
                        chunk.getChunkPosition().y * Chunk.SIZE_Z
                );
                shader.loadModelMatrix(new Matrix4f().translate(pos.x, pos.y, pos.z));
                BakedChunk bakedChunk = chunk.getBakedChunk();
                if (bakedChunk != null) {
                    glActiveTexture(GL_TEXTURE0);
                    glBindTexture(GL_TEXTURE_2D, Texture.TERRAIN);

                    glBindVertexArray(bakedChunk.getVao());

                    glEnableVertexAttribArray(0);
                    glEnableVertexAttribArray(1);
                    glEnableVertexAttribArray(2);

                    glDrawArrays(GL_TRIANGLES, 0, bakedChunk.getVertexCount());

                    glDisableVertexAttribArray(2);
                    glDisableVertexAttribArray(1);
                    glDisableVertexAttribArray(0);

                    glBindVertexArray(0);
                    glBindTexture(GL_TEXTURE_2D, 0);
                    glFlush();
                }
            });
        }

        ShaderProgram.stop();
    }

}
