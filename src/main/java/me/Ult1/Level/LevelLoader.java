package me.Ult1.Level;

import me.Ult1.Main;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

public class LevelLoader {

    public int width, height, style;
    public CharBuffer data;

    public int width_bg, height_bg;
    public CharBuffer data_bg;

    public LevelLoader(String name) throws IOException {
        {
            File levelFile = new File(Main.DATA_PATH + "/levels/" + name);
            BufferedReader reader = new BufferedReader(new FileReader(levelFile));
            String[] params = reader.readLine().split(" ");

            width = Integer.parseUnsignedInt(params[0]);
            height = Integer.parseUnsignedInt(params[1]);
            style = Integer.parseUnsignedInt(params[2]);

            data = BufferUtils.createCharBuffer(width * height + 1);
            reader.read(data);
        }

        File backgroundFile = new File(Main.DATA_PATH + "/levels/" + name + "_bg"); // I sure hope, I won't have > 1 background file!
        if(backgroundFile.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(backgroundFile));
            String[] params = reader.readLine().split(" ");

            width_bg = Integer.parseUnsignedInt(params[0]);
            height_bg = Integer.parseUnsignedInt(params[1]);

//            System.out.println(String.format("width_bg: %d, height_bg: %d", width_bg, height_bg));

            data_bg = BufferUtils.createCharBuffer(width_bg * height_bg + 1);
            reader.read(data_bg);
        }


    }
}
