package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.shaders.ShaderProgram;
import com.mcmacker4.openvoxel.shaders.WorldShader;
import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
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
        shader.loadAmbientLight(world.getAmbientLight());
        world.getChunks().forEach(chunk -> {
            Vector3f pos = new Vector3f(
                    chunk.getChunkPosition().x * Chunk.SIZE_X,
                    0,
                    chunk.getChunkPosition().y * Chunk.SIZE_Z
            );
            shader.loadModelMatrix(new Matrix4f().translate(pos.x, pos.y, pos.z));
            BakedChunk baked = chunk.getBakedChunk();
            if(baked != null) {
                glBindBuffer(GL_ARRAY_BUFFER, baked.vbos[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);
                glEnableClientState(GL_VERTEX_ARRAY);

                glBindBuffer(GL_ARRAY_BUFFER, baked.vbos[1]);
                glTexCoordPointer(2, GL_FLOAT, 0, 0);
                glEnableClientState(GL_TEXTURE_COORD_ARRAY);

                glBindBuffer(GL_ARRAY_BUFFER, baked.vbos[2]);
                glNormalPointer(GL_FLOAT, 0, 0);
                glEnableClientState(GL_NORMAL_ARRAY);

                glDrawArrays(GL_TRIANGLES, 0, baked.vertexCount);

                glBindBuffer(GL_ARRAY_BUFFER, 0);

                glDisableClientState(GL_VERTEX_ARRAY);
                glDisableClientState(GL_TEXTURE_COORD_ARRAY);
                glDisableClientState(GL_NORMAL_ARRAY);
            }
        });
        ShaderProgram.stop();
    }

}
