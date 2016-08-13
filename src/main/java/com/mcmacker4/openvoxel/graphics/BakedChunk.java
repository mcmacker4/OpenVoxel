package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.util.Orientation;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class BakedChunk {

    VertexBuffer vbo;

    public BakedChunk(Chunk chunk) {
        final LinkedList<Vector3f> vertices = new LinkedList<>();
        final LinkedList<Vector2f> texCoords = new LinkedList<>();
        final LinkedList<Vector3f> normals = new LinkedList<>();
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
                            BlockFaceData face = new BlockFaceData(
                                    new Vector3i(x, y, z),
                                    orientation,
                                    block.getTextureID(orientation)
                            );
                            vertices.addAll(face.getVertices());
                            texCoords.addAll(face.getTexCoords());
                            normals.addAll(face.getNormals());
                        }
                    }
                }
            }
        }

        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.size() * (3 + 2 + 3));
        for(int i = 0; i < vertices.size(); i++) {
            Vector3f vertex = vertices.get(i);
            Vector2f texCoord = texCoords.get(i);
            Vector3f normal = normals.get(i);
            buffer.put(new float[] { vertex.x, vertex.y, vertex.z });
            buffer.put(new float[] { texCoord.x, 1 - texCoord.y });
            buffer.put(new float[] { normal.x, normal.y, normal.z });
        }

        buffer.flip();

        vbo = new VertexBuffer(buffer, vertices.size());

    }

    public VertexBuffer getVbo() {
        return vbo;
    }

    public void delete() {
        vbo.delete();
    }

}
