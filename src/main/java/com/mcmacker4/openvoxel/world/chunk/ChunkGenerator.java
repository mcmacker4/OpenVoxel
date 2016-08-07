package com.mcmacker4.openvoxel.world.chunk;

import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import org.joml.Vector2i;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class ChunkGenerator {

    public static Chunk generateChunk(Vector2i position, World world) {
        Block[][][] blocks = new Block[Chunk.SIZE][Chunk.SIZE][Chunk.SIZE];
        for(int x = 0; x < Chunk.SIZE; x++) {
            for(int y = 0; y < Chunk.SIZE; y++) {
                for(int z = 0; z < Chunk.SIZE; z++) {
                    blocks[x][y][z] = Blocks.getById((int) (Math.random() * 3) + 1);
                }
            }
        }
        return new Chunk(blocks, position, world);
    }

}
