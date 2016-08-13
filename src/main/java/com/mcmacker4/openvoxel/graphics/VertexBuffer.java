package com.mcmacker4.openvoxel.graphics;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;

/**
 * Created by McMacker4 on 13/08/2016.
 */
public class VertexBuffer {

    private int id;
    private int vertexCount;

    public VertexBuffer(FloatBuffer data, int vertexCount) {
        id = glGenBuffers();
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        unbind();
        this.vertexCount = vertexCount;
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }

    public void delete() {
        glDeleteBuffers(id);
    }

}
