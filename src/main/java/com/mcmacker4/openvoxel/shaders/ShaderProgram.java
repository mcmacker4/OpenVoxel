package com.mcmacker4.openvoxel.shaders;

import com.mcmacker4.openvoxel.util.FileUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public abstract class ShaderProgram {

    private int program;

    public ShaderProgram(String vSource, String fSource) {
        int vShader = createShader(GL_VERTEX_SHADER, vSource);
        int fShader = createShader(GL_FRAGMENT_SHADER, fSource);
        program = glCreateProgram();
        glAttachShader(program, vShader);
        glAttachShader(program, fShader);
        glLinkProgram(program);
        getUniformLocations();
        if(glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE)
            throw new ShaderException(glGetProgramInfoLog(program));
        glValidateProgram(program);
        if(glGetProgrami(program, GL_VALIDATE_STATUS) != GL_TRUE)
            throw new ShaderException(glGetProgramInfoLog(program));
    }

    protected abstract void getUniformLocations();

    public void start() {
        glUseProgram(program);
    }

    public static void stop() {
        glUseProgram(0);
    }

    protected int getUniformLocation(String name) {
        return glGetUniformLocation(program, name);
    }

    protected void loadFloat(int location, float value) {
        glUniform1f(location, value);
    }

    protected void loadVector3f(int location, Vector3f vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadMatrix4f(int location, Matrix4f matrix) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(16);
        matrix.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }

    private int createShader(int type, String source) {
        if(source.trim().isEmpty())
            throw new IllegalArgumentException("Source code is empty for " + (type == GL_VERTEX_SHADER ? "vertex" : "fragment") + " shader.");
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        if(glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE)
            throw new ShaderException(glGetShaderInfoLog(shader));
        return shader;
    }

}
