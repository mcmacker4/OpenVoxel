package com.mcmacker4.openvoxel.world;

import com.google.common.collect.Sets;
import com.mcmacker4.openvoxel.graphics.Camera;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.chunk.Chunk;
import com.mcmacker4.openvoxel.world.chunk.ChunkGenerator;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Set;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class World {

    public static final int CHUNKS_X = 16, CHUNKS_Z = 16;

    private final Set<Chunk> chunks = Sets.newHashSet();

    private Vector3f lightDir = new Vector3f(0.4f, -1f, -0.8f);

    private Camera camera;
    private ChunkGenerator chunkGen = new ChunkGenerator(this);
    private Thread chunkGenThread = new Thread(chunkGen);

    public World(Camera camera) {
        this.camera = camera;
        chunkGenThread.setDaemon(true);
        chunkGenThread.start();
    }

    public void update() {
        Thread.yield();
    }

    public void setActiveCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getActiveCamera() {
        return camera;
    }

    public Block getBlockAt(Vector3i pos) {
        if(pos.y < 0) return Blocks.STONE;
        if(pos.y >= Chunk.SIZE_Y) return Blocks.AIR;
        Vector2i chunkPos = new Vector2i(pos.x >> 4, pos.z >> 4);
        Vector3i block = new Vector3i(pos.x & 15, pos.y, pos.z & 15);
        for(Chunk chunk : chunks) {
            if(chunk.getChunkPosition().equals(chunkPos)) {
                return chunk.getBlockAt(block);
            }
        }
        return Blocks.STONE;
    }

    public Set<Chunk> getChunks() {
        return chunks;
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
