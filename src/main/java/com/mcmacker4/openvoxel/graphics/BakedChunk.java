package com.mcmacker4.openvoxel.graphics;

import com.mcmacker4.openvoxel.model.Model;
import com.mcmacker4.openvoxel.model.ModelLoader;
import com.mcmacker4.openvoxel.util.Orientation;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.LinkedList;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class BakedChunk {

    private Model model;

    public BakedChunk(Chunk chunk) {
        Block[][][] blocks = chunk.getBlocks();
        LinkedList<BlockFaceData> faces = new LinkedList<>();
        for(int x = 0; x < Chunk.SIZE; x++) {
            for(int y = 0; y < Chunk.SIZE; y++) {
                for(int z = 0; z < Chunk.SIZE; z++) {
                    Block block = blocks[x][y][z];
                    //If block is air, skip it.
                    if(block.getId() == Blocks.AIR.getId())
                        continue;
                    for(Orientation orientation : Orientation.values()) {
                        Vector3i dir = orientation.getDirection();
                        Block neighbour;
                        if(x + dir.x < 0 || x + dir.x >= Chunk.SIZE || z + dir.z < 0 || z + dir.z >= Chunk.SIZE) {
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
        model = ModelLoader.loadModel(vertices, texCoords, normals);
    }

    public Model getModel() {
        return model;
    }

    public void delete() {
        model.delete();
    }

}
