package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.Display;
import com.mcmacker4.openvoxel.Input;
import com.mcmacker4.openvoxel.util.Timer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    private static final float sensitivity = 0.003f;
    private static final float speed = 15f;

    private Matrix4f projectionMatrix;

    public Camera(float fov) {
        position = new Vector3f(0, 0, 2);
        rotation = new Vector3f();
        projectionMatrix = new Matrix4f().setPerspective((float) Math.toRadians(fov), Display.aspectRatio(), 0.1f, 1000f);
    }

    float val = 0;
    public void update() {
        rotation.add(new Vector3f((float) -Input.getDy(), (float) -Input.getDx(), 0).mul(sensitivity));
        if(rotation.x > Math.PI / 2) rotation.x = (float) Math.PI / 2;
        if(rotation.x < -Math.PI / 2) rotation.x = (float) -Math.PI / 2;
        Vector3f movement = new Vector3f();
        if(Input.isKeyDown(GLFW_KEY_A))
            movement.add(getLeftVector());
        if(Input.isKeyDown(GLFW_KEY_S))
            movement.add(getHorizontalBackVector());
        if(Input.isKeyDown(GLFW_KEY_D))
            movement.add(getRightVector());
        if(Input.isKeyDown(GLFW_KEY_W))
            movement.add(getHorizontalFrontVector());
        if(Input.isKeyDown(GLFW_KEY_SPACE))
            movement.add(new Vector3f(0, 1, 0));
        if(Input.isKeyDown(GLFW_KEY_LEFT_SHIFT))
            movement.add(new Vector3f(0, -1, 0));
        movement.mul(speed * (float) Timer.getDelta() * (Input.isKeyDown(GLFW_KEY_LEFT_CONTROL) ? 5 : 1));
        position.add(movement);
    }

    private Vector3f getLookAtVector() {
        Matrix4f transform = new Matrix4f().rotateX(rotation.x).rotateY(rotation.y);
        Vector4f temp = new Vector4f(0, 0, -1, 1);
        temp.mul(transform);
        return new Vector3f(temp.x, temp.y, temp.z).normalize();
    }

    private Vector3f getHorizontalFrontVector() {
        Matrix4f transform = new Matrix4f().rotateY(rotation.y);
        Vector4f temp = new Vector4f(0, 0, -1, 1);
        temp.mul(transform);
        return new Vector3f(temp.x, temp.y, temp.z).normalize();
    }

    private Vector3f getRightVector() {
        Matrix4f transform = new Matrix4f().rotateY((float) Math.toRadians(-90));
        Vector4f temp = new Vector4f(getHorizontalFrontVector(), 1.0f);
        temp.mul(transform);
        return new Vector3f(temp.x, temp.y, temp.z).normalize();
    }

    private Vector3f getLeftVector() {
        Matrix4f transform = new Matrix4f().rotateY((float) Math.toRadians(90));
        Vector4f temp = new Vector4f(getHorizontalFrontVector(), 1.0f);
        temp.mul(transform);
        return new Vector3f(temp.x, temp.y, temp.z).normalize();
    }

    private Vector3f getHorizontalBackVector() {
        Matrix4f transform = new Matrix4f().rotateY((float) Math.toRadians(180));
        Vector4f temp = new Vector4f(getHorizontalFrontVector(), 1.0f);
        temp.mul(transform);
        return new Vector3f(temp.x, temp.y, temp.z).normalize();
    }

    Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    Matrix4f getViewMatrix() {
        return new Matrix4f()
                .rotateX(-rotation.x)
                .rotateY(-rotation.y)
                .rotateZ(-rotation.z)
                .translate(-position.x, -position.y, -position.z);
    }

}
