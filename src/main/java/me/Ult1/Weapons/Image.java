package me.Ult1.Weapons;

import me.Ult1.Main;
import me.Ult1.Vector;
import me.Ult1.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class Image {
    int id; // for the image lwjgl
    int width, height;
    private static int current_max_index;
    private static HashMap<Integer, Image> images = new HashMap<>(16);
    private boolean centered = false;

    public static int
        SAND,
        ROCKET_LAUNCHER;

    static int register(int preferred_index, String path, boolean center) {
        path = Main.DATA_PATH + "/assets/" + path;

        if(!new File(path).exists()){
            System.err.println("Image at path: \"" + path + "\" does not exist!");
            return -1;
        }

        Image image = new Image();
        image.centered = center;

        int actual_index = preferred_index;
        if(preferred_index > current_max_index) current_max_index = preferred_index;
        else actual_index = current_max_index;

        int[] w = new int[1], h = new int[1], channels = new int[1];
        ByteBuffer data = STBImage.stbi_load(path, w, h, channels, 4);

        image.width = w[0]; image.height = h[0];
        System.out.println(channels[0]);
        image.id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, image.id);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.width, image.height, 0, GL11.GL_RGBA, GL_UNSIGNED_BYTE, data);


        data.clear(); // This is lies, deceit and gas-lighting!

        images.put(actual_index, image);

        return actual_index;
    }
    static int register(int preferred_index, String path){
        return register(preferred_index, path, false);
    }

    /**
     * @param position image's position
     * @param frame image's *animation* frame to draw.
     * @param relative whether to draw the image relative to the player or the top left corner of the window.
     * @param rotation (DEGREES). By how much should the image be rotated
     */
    public void draw(Vector position, int frame, boolean relative, int rotation){ // Sprites.GRASS...
        if(!Window.imagesEnabled){
            glEnable(GL_TEXTURE_2D);
            Window.imagesEnabled = true;
        }

        int x = (int) position.getX(),
            y = (int) position.getY();

        if(relative) {
            me.Ult1.Vector temp = position.clone();
            temp = temp.relative();
            x = (int) temp.getX();
            y = (int) temp.getY();
        }

        final double
                ty = (width / (double) height) * frame,
                ty2 = (width / (double) height) * (frame + 1);



        final int x1, x2, y1, y2;

        if(centered){
            x1 = - Window.getScaleX() / 2;
            x2 =   Window.getScaleX() / 2;
            y1 = - Window.getScaleX() / 2;
            y2 =   Window.getScaleY() / 2;
        } else {
            x1 = 0;
            x2 = Window.getScaleX();
            y1 = 0;
            y2 = Window.getScaleY();
        }

        GL11.glPushMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rotation, 0f, 0f, 1);
        // TODO Move this to an actual rotation!

        glBindTexture(GL_TEXTURE_2D, id);
        glBegin(GL_QUADS);
        glTexCoord2d(0, ty);
        glVertex2i(x1 , y1);
        glTexCoord2d(1, ty);
        glVertex2i(x2, y1);
        glTexCoord2d(1, ty2);
        glVertex2i(x2, y2);
        glTexCoord2d(0, ty2);
        glVertex2i(x1, y2);
        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);
        GL11.glPopMatrix();


//        GL11.glRotatef(-45, x, y, 0);
    }

    public void draw(Vector position, int frame, boolean relative){
        draw(position, frame, relative, 0);
    } // I have no idea if I'm supposed to do this, but I can't see any performance difference and so on


    public static void init(){
        SAND = register(0, "tiles/sand.png");
        ROCKET_LAUNCHER = register(20, "Jesus/rocket_launcher.png", true);
    }

    public static Image get(int index){
        return images.get(index);
    }

    public Image setShouldCenter(boolean center){
        centered = center;
        return this;
    }

}
