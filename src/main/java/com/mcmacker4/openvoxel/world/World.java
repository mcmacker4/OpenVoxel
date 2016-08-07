package com.mcmacker4.openvoxel.world;

import com.mcmacker4.openvoxel.world.chunk.Chunk;
import com.mcmacker4.openvoxel.world.chunk.ChunkGenerator;
import org.joml.Vector2i;

import java.util.LinkedList;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class World {

    LinkedList<Chunk> chunks = new LinkedList<>();

    public World() {
        chunks.add(ChunkGenerator.generateChunk(new Vector2i(0, 0), this));
    }

    public void update() {

    }

    public LinkedList<Chunk> getChunks() {
        return chunks;
    }

    private void addChunk(Chunk chunk) {

    }

}
