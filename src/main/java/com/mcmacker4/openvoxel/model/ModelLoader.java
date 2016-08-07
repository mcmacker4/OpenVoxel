package com.mcmacker4.openvoxel.model;

import com.mcmacker4.openvoxel.util.BufferUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class ModelLoader {

    public static Model loadModel(List<Vector3f> vertices, List<Vector2f> texCoords, List<Vector3f> normals) {
        float[] verticesArray = toFloatArray3(vertices);
        float[] texCoordsArray = toFloatArray2(texCoords, true);
        float[] normalsArray = toFloatArray3(normals);
        return loadModel(verticesArray, texCoordsArray, normalsArray);
    }

    public static Model loadModel(float[] vertices, float[] texCoords, float[] normals) {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        int v = storeInVertexArray(0, 3, vertices);
        int t = storeInVertexArray(1, 2, texCoords);
        int n = storeInVertexArray(2, 3, normals);
        glBindVertexArray(0);
        return new Model(vao, new int[] {v, t, n}, vertices.length / 3);
    }

    private static int storeInVertexArray(int index, int size, float[] data) {
        int buffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.floatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return buffer;
    }

    private static float[] toFloatArray3(List<Vector3f> list) {
        float[] array = new float[list.size() * 3];
        int pointer = 0;
        for(Vector3f vec : list) {
            array[pointer++] = vec.x;
            array[pointer++] = vec.y;
            array[pointer++] = vec.z;
        }
        return array;
    }

    private static float[] toFloatArray2(List<Vector2f> list, boolean fixY) {
        float[] array = new float[list.size() * 2];
        int pointer = 0;
        for(Vector2f vec : list) {
            array[pointer++] = vec.x;
            array[pointer++] = fixY ? 1 - vec.y : vec.y;
        }
        return array;
    }

}
