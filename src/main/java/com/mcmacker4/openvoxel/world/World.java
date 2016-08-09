package com.mcmacker4.openvoxel.world;

import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import com.mcmacker4.openvoxel.world.chunk.ChunkGenerator;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.LinkedList;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class World {

    private static final int CHUNKS_X = 8, CHUNKS_Z = 8;

    private LinkedList<Chunk> chunks = new LinkedList<>();

    private Vector3f lightDir = new Vector3f(0.4f, -1f, -0.8f);
    private float ambientValue = 0.4f;

    public World() {
        for(int i =  -CHUNKS_X / 2; i < CHUNKS_X / 2; i++) {
            for(int k = -CHUNKS_Z / 2; k < CHUNKS_Z / 2; k++) {
                chunks.add(ChunkGenerator.generateChunk(new Vector2i(i, k), this));
            }
        }
    }

    public void update() {

    }

    public Block getBlockAt(Vector3i pos) {
        if(pos.y >= Chunk.SIZE_Y) return Blocks.AIR;
        Vector2i chunkPos = new Vector2i(pos.x >> 4, pos.z >> 4);
        for(Chunk chunk : chunks) {
            if(chunk.getChunkPosition().equals(chunkPos)) {
                return chunk.getBlockAt(pos.x & 0xF, pos.y, pos.z & 0xF);
            }
        }
        return Blocks.STONE;
    }

    public LinkedList<Chunk> getChunks() {
        return chunks;
    }

    public float getAmbientLight() {
        return ambientValue;
    }

    private void addChunk(Chunk chunk) {

    }

    public void delete() {
        for(Chunk chunk : chunks) {
            chunk.delete();
        }
    }

    public Vector3f getLightDir() {
        return lightDir;
    }
}
