package com.mcmacker4.openvoxel.world.block;

import java.util.HashMap;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Blocks {

    public static Block AIR, STONE, DIRT, GRASS, MISSING;

    private static HashMap<Integer, Block> blockMap = new HashMap<>();

    private static boolean blocksRegistered = false;

    static {
        if(!blocksRegistered) {
            registerBlock(AIR = new Block(0, new int[] {-1, -1, -1, -1, -1, -1}, true));
            registerBlock(STONE = new Block(1, new int[] {1, 1, 1, 1, 1, 1}));
            registerBlock(DIRT = new Block(2, new int[] {2, 2, 2, 2, 2, 2}));
            registerBlock(GRASS = new Block(3, new int[] {3, 3, 3, 3, 0, 2}));
            registerBlock(MISSING = new Block(4, new int[] {26, 26, 26, 26, 26, 26}));
            blocksRegistered = true;
        }
    }

    private static void registerBlock(Block block) {
        if(blockMap.containsKey(block.getId()))
            throw new IllegalArgumentException("Duplicate block id: " + block.getId());
        blockMap.put(block.getId(), block);
    }

    public static Block getById(int id) {
        if(!blockMap.containsKey(id))
            return AIR;
        return blockMap.get(id);
    }

}
