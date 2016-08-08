package com.mcmacker4.openvoxel.graphics;

import com.google.common.collect.Lists;
import com.mcmacker4.openvoxel.model.ModelLoader;
import com.mcmacker4.openvoxel.util.BufferUtils;
import com.mcmacker4.openvoxel.util.Orientation;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;

/**
 * Created by McMacker4 on 08/08/2016.
 */
public class ChunkVertexData {

    int[] vbos = new int[3];
    int vertexCount;
    boolean init = false;
    public FloatBuffer verticesBuffer;
    public FloatBuffer texCoordsBuffer;
    public FloatBuffer normalsBuffer;

    public ChunkVertexData(Chunk chunk) {
        Block[][][] blocks = chunk.getBlocks();
        List<BlockFaceData> faces = Lists.newLinkedList();
        for (int x = 0; x < Chunk.SIZE_X; x++) {
            for (int y = 0; y < Chunk.SIZE_Y; y++) {
                for (int z = 0; z < Chunk.SIZE_Z; z++) {
                    Block block = blocks[x][y][z];
                    //If block is air, skip it.
                    if (block.getId() == Blocks.AIR.getId())
                        continue;
                    for (Orientation orientation : Orientation.values()) {
                        Vector3i dir = orientation.getDirection();
                        Block neighbour;
                        if (x + dir.x < 0 || x + dir.x >= Chunk.SIZE_X || y + dir.y < 0 || y + dir.y >= Chunk.SIZE_Y || z + dir.z < 0 || z + dir.z >= Chunk.SIZE_Z) {
                            neighbour = chunk.getWorld().getBlockAt(chunk.toWorldCoordinates(new Vector3i(x, y, z).add(dir)));
                        } else {
                            neighbour = chunk.getBlockAt(x + dir.x, y + dir.y, z + dir.z);
                        }
                        //TODO: Check neighbour chunks for face culling.
                        if (neighbour.isTranslucent()) {
                            faces.add(new BlockFaceData(
                                    new Vector3i(x, y, z),
                                    orientation,
                                    block.getTextureID(orientation))
                            );
                        }
                    }
                }
            }
        }
        final ArrayList<Vector3f> vertices = Lists.newArrayList();
        final ArrayList<Vector2f> texCoords = Lists.newArrayList();
        final ArrayList<Vector3f> normals = Lists.newArrayList();
        faces.forEach((face) -> {
            vertices.addAll(face.getVertices());
            texCoords.addAll(face.getTexCoords());
            normals.addAll(face.getNormals());
        });
        vertexCount = vertices.size();
        verticesBuffer = BufferUtils.floatBuffer(ModelLoader.toFloatArray3(vertices));
        texCoordsBuffer = BufferUtils.floatBuffer(ModelLoader.toFloatArray2(texCoords, true));
        normalsBuffer = BufferUtils.floatBuffer(ModelLoader.toFloatArray3(normals));

    }

    public int createVBO(FloatBuffer data) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        return vbo;
    }
}
