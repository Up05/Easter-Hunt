package me.Ult1;

import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class SpriteHandler {

    private static HashMap<Integer, Integer> ids = new HashMap<>();

    public static void loadAll(){
        ArrayList<HashMap<Integer, String>> textures = (ArrayList<HashMap<Integer, String>>) Config.config.get("textures");

        System.out.println(textures);

    }

    private static int load(String path){

        path = Main.DATA_PATH + '\\' + path;

        ByteBuffer buffer;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer
                    w = stack.mallocInt(1),
                    h = stack.mallocInt(1),
                    n = stack.mallocInt(1);

            buffer = STBImage.stbi_load(path, w, h, n, 4);
            if(buffer == null){
                System.err.println("Failed to load texture at path: \"" + path + "\"!");
                return -1;
            }

            int id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(0), h.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            GL30.glGenerateMipmap(id);
            STBImage.stbi_image_free(buffer);
            return id;

        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    } // close to this: https://coffeebeancode.gitbook.io/lwjgl-game-design/tutorials/chapter-2-textures-and-shaders-and-tombstones-oh-wait...
      // although... There's 1 right way to do it ¯\_(ツ)_/¯

    public static int get(int sprite){
        return ids.get(sprite);
    }
}
