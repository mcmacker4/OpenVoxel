package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.util.Orientation;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.MemoryUtil;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class BakedChunk {

    private VertexBuffer[] buffers = new VertexBuffer[3];

    public BakedChunk(Chunk chunk) {
        final ArrayList<BlockFaceData> faces = new ArrayList<>();
        Block[][][] blocks = chunk.getBlocks();
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
                        if(neighbour.getId() == Blocks.AIR.getId()){
                            faces.add(new BlockFaceData(
                                    new Vector3i(x, y, z),
                                    orientation,
                                    block.getTextureID(orientation)
                            ));
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

        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.size() * 3);
        FloatBuffer texCoordsBuffer = MemoryUtil.memAllocFloat(texCoords.size() * 2);
        FloatBuffer normalsBuffer = MemoryUtil.memAllocFloat(normals.size() * 3);

        vertices.forEach((vertex) -> {
            verticesBuffer.put(vertex.x);
            verticesBuffer.put(vertex.y);
            verticesBuffer.put(vertex.z);
        });
        verticesBuffer.flip();
        texCoords.forEach((texCoord) -> {
            texCoordsBuffer.put(texCoord.x);
            texCoordsBuffer.put(texCoord.y);
        });
        texCoordsBuffer.flip();
        normals.forEach((normal) -> {
            normalsBuffer.put(normal.x);
            normalsBuffer.put(normal.y);
            normalsBuffer.put(normal.z);
        });
        normalsBuffer.flip();

        buffers[0] = new VertexBuffer(verticesBuffer, 3);
        buffers[1] = new VertexBuffer(texCoordsBuffer, 2);
        buffers[2] = new VertexBuffer(normalsBuffer, 3);

    }

    public VertexBuffer[] getBuffers() {
        return buffers;
    }

    public void delete() {
        for(VertexBuffer buffer : buffers)
            buffer.delete();
    }

}
