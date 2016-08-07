package com.mcmacker4.openvoxel.util;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class BufferUtils {

    public static FloatBuffer floatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data); buffer.flip();
        return buffer;
    }

    public static IntBuffer intBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data); buffer.flip();
        return buffer;
    }

}
