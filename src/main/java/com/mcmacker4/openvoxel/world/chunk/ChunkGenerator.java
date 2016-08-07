package com.mcmacker4.openvoxel.world.chunk;

import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.generation.SimplexNoise;
import org.joml.Vector3i;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class ChunkGenerator {

    public static Chunk generateChunk(Vector3i position, World world) {
        Block[][][] blocks = new Block[Chunk.SIZE][Chunk.SIZE][Chunk.SIZE];
        for(int x = 0; x < Chunk.SIZE; x++) {
            for(int y = 0; y < Chunk.SIZE; y++) {
                for(int z = 0; z < Chunk.SIZE; z++) {
                    double density = SimplexNoise.noise(
                            (double) (x + (position.x * Chunk.SIZE)) / 40,
                            (double) (y + (position.y * Chunk.SIZE)) / 40,
                            (double) (z + (position.z * Chunk.SIZE)) / 40
                    );
                    density += (-(position.y * Chunk.SIZE + y) - 20) / 100;
                    if(density > 0) blocks[x][y][z] = Blocks.GRASS;
                    else blocks[x][y][z] = Blocks.AIR;
                }
            }
        }
        return new Chunk(blocks, position, world);
    }

}
