package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.model.Model;
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
    private Camera camera;

    public WorldRenderer(World world, WorldShader shader, Camera camera) {
        this.world = world;
        this.shader = shader;
        this.camera = camera;
        shader.start();
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        ShaderProgram.stop();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void render() {
        shader.start();
        shader.loadViewMatrix(camera.getViewMatrix());
        world.getChunks().forEach(chunk -> {
            Vector3f pos = new Vector3f(chunk.getChunkPosition().x * Chunk.SIZE, 0, chunk.getChunkPosition().y * Chunk.SIZE);
            shader.loadModelMatrix(new Matrix4f().translate(pos.x, 0, pos.z));
            Model model = chunk.getBakedChunk().getModel();
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, Texture.TERRAIN);
            glBindVertexArray(model.getVao());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);
            glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
            glBindTexture(GL_TEXTURE_2D, 0);
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
        });
        glBindVertexArray(0);
        ShaderProgram.stop();
    }

}
