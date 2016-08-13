package com.mcmacker4.openvoxel.world.generation;

/**
 * Created by McMacker4 on 10/08/2016.
 */
public class SimplexOctave {

    private SimplexNoise noise;

    private double freq, amp;

    public SimplexOctave(double freq, double amp, int seed) {
        noise = new SimplexNoise(seed);
        this.freq = freq;
        this.amp = amp;
    }

    public double get(double x, double y) {
        return amp * noise.eval(x * freq, y * freq);
    }

    public double get(double x, double y, double z) {
        return amp * noise.eval(x * freq, y * freq, z * freq);
    }

}
