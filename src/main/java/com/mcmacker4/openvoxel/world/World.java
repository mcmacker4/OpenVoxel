package com.mcmacker4.openvoxel.world;

import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import com.mcmacker4.openvoxel.world.chunk.ChunkGenerator;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.LinkedList;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class World {

    private static final int CHUNKS_X = 24, CHUNKS_Y = 4, CHUNKS_Z = 24;

    private LinkedList<Chunk> chunks = new LinkedList<>();

    Vector3f lightDir = new Vector3f(0.4f, -1f, -0.8f);

    public World() {
        for(int i = 0; i < CHUNKS_X; i++) {
            for(int j = 0; j < CHUNKS_Y; j++) {
                for(int k = 0; k < CHUNKS_Z; k++) {
                    chunks.add(ChunkGenerator.generateChunk(new Vector3i(i, j, k), this));
                }
            }
        }
    }

    public void update() {

    }

    public Block getBlockAt(Vector3i pos) {
        Vector3i lookingFor = new Vector3i(pos.x / Chunk.SIZE, pos.y / Chunk.SIZE, pos.z / Chunk.SIZE);
        for(Chunk chunk : chunks) {
            if(chunk.getChunkPosition().equals(lookingFor)) {
                return chunk.getBlockAt(pos.x % Chunk.SIZE, pos.y % Chunk.SIZE, pos.z % Chunk.SIZE);
            }
        }
        return Blocks.AIR;
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
