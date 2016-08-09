package com.mcmacker4.openvoxel.world.chunk;

import com.mcmacker4.openvoxel.graphics.BakedChunk;
import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import org.joml.Vector2i;
import org.joml.Vector3i;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Chunk {

    public static final int SIZE_X = 16;
    public static final int SIZE_Y = 128;
    public static final int SIZE_Z = 16;

    private Block[][][] blocks = new Block[SIZE_X][SIZE_Y][SIZE_Z];
    private BakedChunk bakedChunk;

    private Vector2i chunkPosition;

    private World world;

    private boolean hasToBake;

    public Chunk(Block[][][] blocks, Vector2i chunkPosition, World world) {
        this.blocks = blocks;
        this.chunkPosition = chunkPosition;
        this.world = world;
        hasToBake = true;
    }

    private void bake() {
        hasToBake = false;
        if(bakedChunk != null)
            bakedChunk.delete();
        bakedChunk = new BakedChunk(this);
    }

    public Block[][][] getBlocks() {
        return blocks;
    }

    /**
     * Returns the block at the coordinates specified (in chunk coordinates)
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @return the block at specified coordinates
     */
    public Block getBlockAt(int x, int y, int z) {
        if(y >= Chunk.SIZE_Y) return Blocks.AIR;
        if(x < 0 || x >= Chunk.SIZE_X || y < 0 || z < 0 || z >= Chunk.SIZE_Z)
            return Blocks.STONE;
        return blocks[x][y][z];
    }

    public Vector3i toWorldCoordinates(Vector3i vector) {
        return new Vector3i((chunkPosition.x * SIZE_X) + vector.x, vector.y, (chunkPosition.y * SIZE_Z) + vector.z);
    }

    public BakedChunk getBakedChunk() {
        if(hasToBake) bake();
        return bakedChunk;
    }

    public World getWorld() {
        return world;
    }

    public Vector2i getChunkPosition() {
        return chunkPosition;
    }

    public void delete() {
        bakedChunk.delete();
    }

}
