package com.mcmacker4.openvoxel.model;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Model {

    private int vao;
    private int[] vbos;
    private int vertexCount;

    Model(int vao, int[] vbos, int vertexCount) {
        this.vao = vao;
        this.vbos = vbos;
        this.vertexCount = vertexCount;
    }

    public int getVao() {
        return vao;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void delete() {
        glDeleteVertexArrays(vao);
        for(int vbo : vbos) glDeleteBuffers(vbo);
    }

}
