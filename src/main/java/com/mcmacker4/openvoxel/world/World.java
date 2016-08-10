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

    private static final int CHUNKS_X = 16, CHUNKS_Z = 16;

    private LinkedList<Chunk> chunks = new LinkedList<>();

    Vector3f lightDir = new Vector3f(0.4f, -1f, -0.8f);

    public World() {
        for(int i = 0; i < CHUNKS_X; i++) {
            for(int k = 0; k < CHUNKS_Z; k++) {
                chunks.add(ChunkGenerator.generateChunk(new Vector2i(i, k), this));
            }
        }
    }

    public void update() {

    }

    public Block getBlockAt(Vector3i pos) {
        Vector2i lookingFor = new Vector2i(pos.x >> 4, pos.z >> 4);
        for(Chunk chunk : chunks) {
            if(chunk.getChunkPosition().equals(lookingFor)) {
                return chunk.getBlockAt(pos.x & 0xF, pos.y, pos.z & 0xF);
            }
        }
        return Blocks.STONE;
    }

    public LinkedList<Chunk> getChunks() {
        return chunks;
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
