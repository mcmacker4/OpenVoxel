package com.mcmacker4.openvoxel.util;

import org.joml.Vector3i;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public enum Orientation {

    NORTH(0, new Vector3i(0, 0, -1)),   //-Z
    SOUTH(1, new Vector3i(0, 0, 1)),   //+Z
    EAST(2, new Vector3i(1, 0, 0)),    //+X
    WEST(3, new Vector3i(-1, 0, 0)),    //-X
    UP(4, new Vector3i(0, 1, 0)),      //+Y
    DOWN(5, new Vector3i(0, -1, 0));    //-Y

    int id;
    Vector3i direction;

    Orientation(int id, Vector3i direction) {
        this.id = id;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public Vector3i getDirection() {
        return direction;
    }
}
