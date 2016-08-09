package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.util.BufferUtils;
import com.mcmacker4.openvoxel.util.Orientation;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class BakedChunk {

    int[] vbos = new int[3];

    int vertexCount;

    public BakedChunk(Chunk chunk) {
        Block[][][] blocks = chunk.getBlocks();
        LinkedList<BlockFaceData> faces = new LinkedList<>();
        for(int x = 0; x < Chunk.SIZE_X; x++) {
            for(int y = 0; y < Chunk.SIZE_Y; y++) {
                for(int z = 0; z < Chunk.SIZE_Z; z++) {
                    Block block = blocks[x][y][z];
                    //If block is air, skip it.
                    if(block.getId() == Blocks.AIR.getId())
                        continue;
                    for(Orientation orientation : Orientation.values()) {
                        Vector3i dir = orientation.getDirection();
                        Block neighbour;
                        if(x + dir.x < 0 || x + dir.x >= Chunk.SIZE_X || y + dir.y < 0 || y + dir.y >= Chunk.SIZE_Y || z + dir.z < 0 || z + dir.z >= Chunk.SIZE_Z) {
                            neighbour = chunk.getWorld().getBlockAt(chunk.toWorldCoordinates(new Vector3i(x, y, z).add(dir)));
                        } else {
                            neighbour = chunk.getBlockAt(x + dir.x, y + dir.y, z + dir.z);
                        }
                        //TODO: Check neighbour chunks for face culling.
                        if(neighbour.getId() == Blocks.AIR.getId()){
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
        final LinkedList<Vector3f> vertices = new LinkedList<>();
        final LinkedList<Vector2f> texCoords = new LinkedList<>();
        final LinkedList<Vector3f> normals = new LinkedList<>();
        faces.forEach((face) -> {
            vertices.addAll(face.getVertices());
            texCoords.addAll(face.getTexCoords());
            normals.addAll(face.getNormals());
        });

        vertexCount = vertices.size();

        glGenBuffers(vbos);
        glBindBuffer(GL_ARRAY_BUFFER, vbos[0]);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.floatBuffer(toArray3(vertices)), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, vbos[1]);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.floatBuffer(toArray2(texCoords, true)), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, vbos[2]);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.floatBuffer(toArray3(normals)), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

    }

    public void delete() {
        for(int vbo : vbos)
            glDeleteBuffers(vbo);
    }

    private static float[] toArray3(List<Vector3f> list) {
        float[] array = new float[list.size() * 3];
        int pointer = 0;
        for(Vector3f vec : list) {
            array[pointer++] = vec.x;
            array[pointer++] = vec.y;
            array[pointer++] = vec.z;
        }
        return array;
    }

    private static float[] toArray2(List<Vector2f> list, boolean fixTexCoords) {
        float[] array = new float[list.size() * 2];
        int pointer = 0;
        for(Vector2f vec : list) {
            array[pointer++] = vec.x;
            array[pointer++] = fixTexCoords ? 1 - vec.y : vec.y;
        }
        return array;
    }

}
