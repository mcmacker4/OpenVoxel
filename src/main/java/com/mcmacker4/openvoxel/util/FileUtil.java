package com.mcmacker4.openvoxel.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by McMacker4 on 05/08/2016.
 */
public class FileUtil {

    public static String readFullFile(String name) {
        final StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(name);
            if (inputStream == null)
                throw new FileNotFoundException("No such file: " + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.lines().forEach(line -> builder.append(line).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
