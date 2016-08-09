package com.mcmacker4.openvoxel.world.chunk;

import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.generation.SimplexNoise;
import com.mcmacker4.openvoxel.world.generation.SimplexNoise_octave;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.Random;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class ChunkGenerator {

    private static SimplexNoise noise = new SimplexNoise(62, 0.2, 0);

    static Random rand = new Random();

    public static Chunk generateChunk(Vector2i position, World world) {
        Block[][][] blocks = new Block[Chunk.SIZE_X][Chunk.SIZE_Y][Chunk.SIZE_Z];
        for(int x = 0; x < Chunk.SIZE_X; x++) {
            for(int z = 0; z < Chunk.SIZE_Z; z++) {
                for(int y = Chunk.SIZE_Y - 1; y >= 0; y--) {
                    blocks[x][y][z] = Blocks.getById(rand.nextInt(4));
//                    Vector3i worldPos = new Vector3i(x, y, z).add(new Vector3i(position.x, 0, position.y).mul(Chunk.SIZE_X, Chunk.SIZE_Y, Chunk.SIZE_Z));
//                    int val = (int) (noise.getNoise(worldPos.x, worldPos.y, worldPos.z) * 64 + 70 - worldPos.y);
//                    blocks[x][y][z] = Blocks.AIR;
//                    if(val >= 0) {
//                        if (blocks[x][y+1][z] == Blocks.AIR) {
//                            blocks[x][y][z] = Blocks.GRASS;
//                        } else {
//                            blocks[x][y][z] = Blocks.DIRT;
//                        }
//                    }
                }
            }
        }
        return new Chunk(blocks, position, world);
    }

}
