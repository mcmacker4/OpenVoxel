package com.mcmacker4.openvoxel.world.chunk;

import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.generation.SimplexOctave;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.Random;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class ChunkGenerator {

    private static Random rand = new Random();

    private static final int WATER_LEVEL = 64;
    private static SimplexOctave mainOctave = new SimplexOctave(0.05, 10, rand.nextInt());
    private static SimplexOctave perturb = new SimplexOctave(0.005, 5, rand.nextInt());

    private static SimplexOctave caves = new SimplexOctave(0.1, 10, rand.nextInt());
    private static double CAVE_VALUE = 3;

    public static Chunk generateChunk(Vector2i position, World world) {
        Block[][][] blocks = new Block[Chunk.SIZE_X][Chunk.SIZE_Y][Chunk.SIZE_Z];
        for(int x = 0; x < Chunk.SIZE_X; x++) {
            for(int z = 0; z < Chunk.SIZE_Z; z++) {
                for(int y = Chunk.SIZE_Y - 1; y >= 0; y--) {
                    Vector3i pos = new Vector3i(x, y, z).add(new Vector3i(position.x * Chunk.SIZE_X, 0, position.y * Chunk.SIZE_Z));
                    //Heightmap
                    double v1 = perturb.get(pos.x, pos.z);
                    int height = (int) (mainOctave.get(pos.x + v1, pos.z + v1) + WATER_LEVEL);
                    //Caves
                    double c1 = perturb.get(pos.x, pos.y, pos.z) * 5;
                    double density = caves.get(pos.x + c1, pos.y + c1, pos.z + c1) + CAVE_VALUE + (pos.y / 20);
                    if(density > 0 && pos.y <= height)
                        blocks[x][y][z] = pos.y == height ? Blocks.GRASS : Blocks.STONE;
                    else
                        blocks[x][y][z] = Blocks.AIR;
                }
            }
        }
        return new Chunk(blocks, position, world);
    }

}
