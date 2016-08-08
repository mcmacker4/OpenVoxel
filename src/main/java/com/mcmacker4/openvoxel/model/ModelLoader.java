package com.mcmacker4.openvoxel.model;

import com.mcmacker4.openvoxel.util.BufferUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GLXStereoNotifyEventEXT;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
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
        return loadModel(
                BufferUtils.floatBuffer(vertices),
                BufferUtils.floatBuffer(texCoords),
                BufferUtils.floatBuffer(normals),
                vertices.length / 3
        );
    }

    public static Model loadModel(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, int vertexCount) {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        int v = storeInVertexArray(0, 3, vertices);
        int t = storeInVertexArray(1, 2, texCoords);
        int n = storeInVertexArray(2, 3, normals);
        glBindVertexArray(0);
        return new Model(vao, new int[] {v, t, n}, vertexCount);
    }

    private static int storeInVertexArray(int index, int size, FloatBuffer data) {
        int buffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return buffer;
    }

    public static float[] toFloatArray3(List<Vector3f> list) {
        float[] array = new float[list.size() * 3];
        int pointer = 0;
        for(Vector3f vec : list) {
            array[pointer++] = vec.x;
            array[pointer++] = vec.y;
            array[pointer++] = vec.z;
        }
        return array;
    }

    public static float[] toFloatArray2(List<Vector2f> list, boolean fixY) {
        float[] array = new float[list.size() * 2];
        int pointer = 0;
        for(Vector2f vec : list) {
            array[pointer++] = vec.x;
            array[pointer++] = fixY ? 1 - vec.y : vec.y;
        }
        return array;
    }

}
