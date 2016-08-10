package com.mcmacker4.openvoxel.graphics;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Created by McMacker4 on 10/08/2016.
 */
public class VertexBuffer {

    private int glid;
    private int vertexSize;
    private int vertexCount;

    public VertexBuffer(FloatBuffer data, int vertexSize) {
        glid = glGenBuffers();
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        unbind();
        this.vertexSize = vertexSize;
        vertexCount = data.remaining() / vertexSize;
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, glid);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void delete() {
        glDeleteBuffers(glid);
    }

    public int getVertexSize() {
        return vertexSize;
    }

    public int getVertexCount() {
        return vertexCount;
    }

}
