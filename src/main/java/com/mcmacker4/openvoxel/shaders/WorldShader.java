package com.mcmacker4.openvoxel.shaders;

import com.mcmacker4.openvoxel.util.FileUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class WorldShader extends ShaderProgram {

    private int loc_ProjectionMatrix;
    private int loc_ViewMatrix;
    private int loc_ModelMatrix;
    private int loc_lightDir;
    private int loc_ambientLight;

    public WorldShader() {
        super(
                FileUtil.readFullFile("shaders/WorldShader.v.glsl"),
                FileUtil.readFullFile("shaders/WorldShader.f.glsl")
        );
    }

    @Override
    protected void getUniformLocations() {
        loc_ProjectionMatrix = getUniformLocation("projectionMatrix");
        loc_ViewMatrix = getUniformLocation("viewMatrix");
        loc_ModelMatrix = getUniformLocation("modelMatrix");
        loc_lightDir = getUniformLocation("lightDir");
        loc_ambientLight = getUniformLocation("ambientLight");
    }

    public void loadViewMatrix(Matrix4f viewMatrix) {
        loadMatrix4f(loc_ViewMatrix, viewMatrix);
    }

    public void loadModelMatrix(Matrix4f matrix) {
        loadMatrix4f(loc_ModelMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f projectionMatrix) {
        loadMatrix4f(loc_ProjectionMatrix, projectionMatrix);
    }

    public void loadLightDir(Vector3f lightDir) {
        loadVector3f(loc_lightDir, lightDir);
    }

    public void loadAmbientLight(float value) {
        loadFloat(loc_ambientLight, value);
    }
}
