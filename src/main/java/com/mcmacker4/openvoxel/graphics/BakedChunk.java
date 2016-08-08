package com.mcmacker4.openvoxel.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class BakedChunk {

    int vao;
    int vertexCount;

    private ChunkVertexData data;

    public BakedChunk(ChunkVertexData data) {
        this.data = data;
        vertexCount = data.vertexCount;
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        for(int i = 0; i < 3; i++) {
            glBindBuffer(GL_ARRAY_BUFFER, data.vbos[i]);
            glVertexAttribPointer(i, (i == 1) ? 2 : 3, GL_FLOAT, false, 0, 0);
        }
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getVao() {
        return vao;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void delete() {
        for(int vbo : data.vbos)
            glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
    }

}
