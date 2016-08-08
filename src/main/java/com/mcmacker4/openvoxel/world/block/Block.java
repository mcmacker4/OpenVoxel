package com.mcmacker4.openvoxel.world.block;

import com.mcmacker4.openvoxel.util.Orientation;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class Block {

    private int id;
    private int[] textureIDs;

    private boolean translucent;

    /*
    textureIDs[] should be an array of size 6 with the textureID of each texture for each face.
    The index -> face mapping is done using Orientation.id. Each id represents each face of the cube
    from 0 to 5.
     */
    Block(int id, int[] textureIDs, boolean translucent) {
        if(textureIDs.length != 6)
            throw new IllegalArgumentException("Argument textureIDs should be of size 6. It is not.");
        this.id = id;
        this.textureIDs = textureIDs;
        this.translucent = translucent;
    }

    Block(int id, int[] textureIDs) {
        this(id, textureIDs, false);
    }

    public int getId() {
        return id;
    }

    public boolean isTranslucent() {
        return translucent;
    }

    public int getTextureID(Orientation orientation) {
        return textureIDs[orientation.getId()];
    }

}
