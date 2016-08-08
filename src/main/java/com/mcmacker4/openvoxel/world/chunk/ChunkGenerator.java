package com.mcmacker4.openvoxel.world.chunk;

import com.mcmacker4.openvoxel.Display;
import com.mcmacker4.openvoxel.world.World;
import com.mcmacker4.openvoxel.world.block.Block;
import com.mcmacker4.openvoxel.world.block.Blocks;
import com.mcmacker4.openvoxel.world.generation.SimplexNoise;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL;

import java.util.Iterator;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class ChunkGenerator implements Runnable {

    private World world;

    private Random rand = new Random(245);
    private SimplexNoise noise = new SimplexNoise(64, 0.1, 0);

    private final int RENDER_DIST = 4;

    public ChunkGenerator(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        glfwMakeContextCurrent(Display.getWorkerWindow());
        GL.createCapabilities();
        while(!Display.shouldClose()) {
            final Vector3f camPos = world.getActiveCamera().getPosition();
            final Vector2i closestChunk = new Vector2i(((int) camPos.x) >> 4, ((int) camPos.z) >> 4);
            unloadUnwantedChunks(closestChunk);
            addNecesaryChunks(closestChunk);
            updateChunks();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void unloadUnwantedChunks(Vector2i closestChunk) {
        synchronized (world.getChunks()) {
            Iterator<Chunk> it = world.getChunks().iterator();
            while (it.hasNext()) {
                Chunk chunk = it.next();
                if (chunk.getChunkPosition().distance(closestChunk) > RENDER_DIST + 2) {
                    chunk.delete();
                    it.remove();
                    return;
                }
            }
        }
    }

    private void addNecesaryChunks(Vector2i closestChunk) {
        for(int x = closestChunk.x - RENDER_DIST; x <= closestChunk.x + RENDER_DIST; x++) {
            for(int z = closestChunk.y - RENDER_DIST; z <= closestChunk.y + RENDER_DIST; z++) {
                Vector2i chunkPos = new Vector2i(x, z);
                if(!isChunkLoaded(chunkPos)) {
                    synchronized (world.getChunks()) {
                        world.getChunks().add(generateChunk(chunkPos));
                        return;
                    }
                }
            }
        }
    }

    public void updateChunks() {
        synchronized (world.getChunks()) {
            world.getChunks().stream().filter(Chunk::hasToCompile).forEach(Chunk::compile);
        }
    }

    private Chunk generateChunk(Vector2i position) {
        Block[][][] blocks = new Block[Chunk.SIZE_X][Chunk.SIZE_Y][Chunk.SIZE_Z];
        for(int x = 0; x < Chunk.SIZE_X; x++) {
            for(int z = 0; z < Chunk.SIZE_Z; z++) {
                boolean topBlock = true;
                for(int y = Chunk.SIZE_Y - 1; y >= 0; y--) {
                    Vector3i worldPos = new Vector3i(x, y, z).add(new Vector3i(position.x, 0, position.y).mul(Chunk.SIZE_X, Chunk.SIZE_Y, Chunk.SIZE_Z));
                    int val = (int) (noise.getNoise(worldPos.x, worldPos.y, worldPos.z) * 64 + 64 - worldPos.y);
                    blocks[x][y][z] = Blocks.AIR;
                    if(val >= 0) {
                        if (topBlock) {
                            blocks[x][y][z] = Blocks.GRASS;
                            topBlock = false;
                        } else {
                            blocks[x][y][z] = Blocks.DIRT;
                        }
                    }
                }
            }
        }
        return new Chunk(blocks, position, world);
    }

    private boolean isChunkLoaded(Vector2i vec) {
        synchronized (world.getChunks()) {
            for (Chunk chunk : world.getChunks()) {
                if (chunk.getChunkPosition().equals(vec))
                    return true;
            }
        }
        return false;
    }

}
