package com.mcmacker4.openvoxel.world.chunk;

import com.mcmacker4.openvoxel.graphics.BakedChunk;
import com.mcmacker4.openvoxel.graphics.ChunkVertexData;
import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.block.Block;
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
    private ChunkVertexData vertexData;
    private BakedChunk bakedChunk;

    private Vector2i chunkPosition;

    private World world;

    private boolean hasToCompile;
    private boolean hasToBake;

    public Chunk(Block[][][] blocks, Vector2i chunkPosition, World world) {
        this.blocks = blocks;
        this.chunkPosition = chunkPosition;
        this.world = world;
        hasToCompile = true;
    }

    public void compile() {
        hasToCompile = false;
        vertexData = new ChunkVertexData(this);
        hasToBake = true;
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
        if(x < 0 || x >= Chunk.SIZE_X || y < 0 || y >= Chunk.SIZE_Y || z < 0 || z >= Chunk.SIZE_Z)
            throw new IllegalArgumentException("Invalid chunk coordinates: (" + x + ", " + y + ", " + z + ").");
        return blocks[x][y][z];
    }

    public Block getBlockAt(Vector3i pos) {
        return getBlockAt(pos.x, pos.y, pos.z);
    }

    public Vector3i toWorldCoordinates(Vector3i vector) {
        return new Vector3i((chunkPosition.x * SIZE_X) + vector.x, vector.y, (chunkPosition.y * SIZE_Z) + vector.z);
    }

    public boolean hasToCompile() {
        return hasToCompile;
    }

    public BakedChunk getBakedChunk() {
        if(hasToBake) {
            if(bakedChunk != null)
                bakedChunk.delete();
            if(vertexData != null)
                bakedChunk = new BakedChunk(vertexData);
        }
        return bakedChunk;
    }

    public World getWorld() {
        return world;
    }

    public Vector2i getChunkPosition() {
        return chunkPosition;
    }

    public void delete() {
        if(bakedChunk != null)
            bakedChunk.delete();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Chunk))
            return false;
        Chunk c = (Chunk) obj;
        return this.chunkPosition.equals(c.getChunkPosition());
    }
}
