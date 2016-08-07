package com.mcmacker4.openvoxel.world.block;

import com.mcmacker4.openvoxel.util.Orientation;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Block {

    private int id;
    private int textureIDs[];

    Block(int id, int textureIDs[]) {
        this.id = id;
        this.textureIDs = textureIDs;
    }

    public int getId() {
        return id;
    }

    public int getTextureID(Orientation orientation) {
        return textureIDs[orientation.getId()];
    }

}
