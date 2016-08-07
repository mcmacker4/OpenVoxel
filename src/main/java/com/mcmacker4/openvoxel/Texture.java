package com.mcmacker4.openvoxel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by McMacker4 on 07/08/2016.
 */
public class Texture {

    public static int TERRAIN;

    public static void loadTextures() {
        TERRAIN = loadTexture("textures/terrain_good.png");
    }

    private static int loadTexture(String name) {

        int textureID = 0;

        try {

            InputStream stream = Texture.class.getClassLoader().getResourceAsStream(name);
            if (stream == null)
                throw new FileNotFoundException("No such file: " + name);

            BufferedImage image = ImageIO.read(stream);

            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

            for(int y = 0; y < image.getHeight(); y++){
                for(int x = 0; x < image.getWidth(); x++){
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

            buffer.flip();

            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return textureID;

    }

}
