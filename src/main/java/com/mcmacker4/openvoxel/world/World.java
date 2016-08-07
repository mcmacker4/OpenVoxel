package com.mcmacker4.openvoxel.world;

import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import com.mcmacker4.openvoxel.world.chunk.ChunkGenerator;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.LinkedList;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class World {

    LinkedList<Chunk> chunks = new LinkedList<>();

    public World() {
        chunks.add(ChunkGenerator.generateChunk(new Vector2i(0, 0), this));
        chunks.add(ChunkGenerator.generateChunk(new Vector2i(0, 1), this));
        chunks.add(ChunkGenerator.generateChunk(new Vector2i(1, 0), this));
    }

    public void update() {

    }

    public Block getBlockAt(Vector3i pos) {
        Vector2i lookingFor = new Vector2i(pos.x / Chunk.SIZE, pos.z / Chunk.SIZE);
        System.out.println("Block pos: " + pos + " Calculated chunk: " + lookingFor);
        for(Chunk chunk : chunks) {
            if(chunk.getChunkPosition().equals(lookingFor)) {
                return chunk.getBlockAt(pos.x % Chunk.SIZE, pos.y, pos.z % Chunk.SIZE);
            }
        }
        return Blocks.AIR;
    }

    public LinkedList<Chunk> getChunks() {
        return chunks;
    }

    private void addChunk(Chunk chunk) {

    }

}
