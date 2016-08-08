package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.util.Orientation;
import org.joml.*;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by McMacker4 on 06/08/2016.
 */
public class BlockFaceData {

    private Vector<Vector3f> vertices = new Vector<>();
    private Vector<Vector2f> texCoords = new Vector<>();
    private Vector<Vector3f> normals = new Vector<>();

    public BlockFaceData(Vector3i blockPos, Orientation orientation, int textureID) {
        //Vertices according to orientation of the face
        switch(orientation) {
            case NORTH:
                vertices.add(new Vector3f(1, 1, 0));
                vertices.add(new Vector3f(1, 0, 0));
                vertices.add(new Vector3f(0, 0, 0));
                vertices.add(new Vector3f(1, 1, 0));
                vertices.add(new Vector3f(0, 0, 0));
                vertices.add(new Vector3f(0, 1, 0));
                break;
            case SOUTH:
                vertices.add(new Vector3f(0, 1, 1));
                vertices.add(new Vector3f(0, 0, 1));
                vertices.add(new Vector3f(1, 0, 1));
                vertices.add(new Vector3f(0, 1, 1));
                vertices.add(new Vector3f(1, 0, 1));
                vertices.add(new Vector3f(1, 1, 1));
                break;
            case EAST:
                vertices.add(new Vector3f(1, 1, 1));
                vertices.add(new Vector3f(1, 0, 1));
                vertices.add(new Vector3f(1, 0, 0));
                vertices.add(new Vector3f(1, 1, 1));
                vertices.add(new Vector3f(1, 0, 0));
                vertices.add(new Vector3f(1, 1, 0));
                break;
            case WEST:
                vertices.add(new Vector3f(0, 1, 0));
                vertices.add(new Vector3f(0, 0, 0));
                vertices.add(new Vector3f(0, 0, 1));
                vertices.add(new Vector3f(0, 1, 0));
                vertices.add(new Vector3f(0, 0, 1));
                vertices.add(new Vector3f(0, 1, 1));
                break;
            case UP:
                vertices.add(new Vector3f(0, 1, 0));
                vertices.add(new Vector3f(0, 1, 1));
                vertices.add(new Vector3f(1, 1, 1));
                vertices.add(new Vector3f(0, 1, 0));
                vertices.add(new Vector3f(1, 1, 1));
                vertices.add(new Vector3f(1, 1, 0));
                break;
            case DOWN:
                vertices.add(new Vector3f(0, 0, 1));
                vertices.add(new Vector3f(0, 0, 0));
                vertices.add(new Vector3f(1, 0, 0));
                vertices.add(new Vector3f(0, 0, 1));
                vertices.add(new Vector3f(1, 0, 0));
                vertices.add(new Vector3f(1, 0, 1));
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation. How the fuck did you do that?");
        }
        //Translate all verticesBuffer to position
        final Matrix4f vertTransform = new Matrix4f().translate(new Vector3f(blockPos.x, blockPos.y, blockPos.z));
        vertices.forEach((vertex) -> {
            Vector4f vec = new Vector4f(vertex, 1f);
            vec.mul(vertTransform);
            vertex.set(vec.x, vec.y, vec.z);
        });

        //Normals according to orientation
        switch(orientation) {
            case NORTH:
                for(int i = 0; i < 6; i++)
                    normals.add(new Vector3f(0, 0, 1));
                break;
            case SOUTH:
                for(int i = 0; i < 6; i++)
                    normals.add(new Vector3f(0, 0, -1));
                break;
            case EAST:
                for(int i = 0; i < 6; i++)
                    normals.add(new Vector3f(1, 0, 0));
                break;
            case WEST:
                for(int i = 0; i < 6; i++)
                    normals.add(new Vector3f(-1, 0, 0));
                break;
            case UP:
                for(int i = 0; i < 6; i++)
                    normals.add(new Vector3f(0, 1, 0));
                break;
            case DOWN:
                for(int i = 0; i < 6; i++)
                    normals.add(new Vector3f(0, -1, 0));
                break;
        }

        //
        texCoords.add(new Vector2f(0, 1));
        texCoords.add(new Vector2f(0, 0));
        texCoords.add(new Vector2f(1, 0));
        texCoords.add(new Vector2f(0, 1));
        texCoords.add(new Vector2f(1, 0));
        texCoords.add(new Vector2f(1, 1));

        //Scale coords to fit one 16th of the whole texture
        //and translate coords to position
        int ix = textureID % 16;
        int iy = (16 - 1) - (textureID / 16);
        float x = (float) ix / 16;
        float y = (float) iy / 16;
        texCoords.forEach((vec) -> {
            vec.mul((float) 1/16);
            vec.add(x, y);
        });

    }

    public Vector<Vector3f> getVertices() {
        return vertices;
    }

    public Vector<Vector2f> getTexCoords() {
        return texCoords;
    }

    public Vector<Vector3f> getNormals() {
        return normals;
    }

}
