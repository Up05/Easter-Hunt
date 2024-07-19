package me.Ult1;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

public class Text {

    public String text;
    public static int w, h;
    public static int id = -1;

    public static BufferedImage font;

    public static void init(){
        if(font == null){
            try {
//                font = ImageIO.read(new File("C:\\Users\\Augustas\\Desktop\\c_Java\\Graphics\\Pong-LWJGL\\Lucida Console.png"));
                font = ImageIO.read(new File(Main.DATA_PATH + "/assets/font/font.png"));

                w = font.getWidth();
                h = font.getHeight();

                int colorComps = font.getColorModel().getNumComponents();

                byte[] data = new byte[colorComps * w * h];
                font.getRaster().getDataElements(0, 0, w, h, data);

                ByteBuffer pixels = BufferUtils.createByteBuffer(data.length);
                pixels.put(data);
                pixels.rewind();

                id = GL11.glGenTextures();

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
                pixels.clear();


            } catch (NullPointerException e) {
                System.out.println("font.png doesn't exist, for some reason???");
                System.out.println(new File("assets/font/font.png").getAbsolutePath()); // @TODO aghh!
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int x, y, cw = 16, ch = 16, line_n = 0, line_max = 0; // c_har w_idth; line_number (the number of new lines... yep); lime_max max chars in all lines
    private int cs = 16; // character spacing
    boolean shouldCenter = false, shouldCenterY, isAbsolute = true;
    public Text(String text, int x, int y){
        this.text = text; this.x = x; this.y = y;

//        line_n = (int) text.chars().filter(ch -> ch == '\n').count();
        final int[] n = {0}; // pointer variables, not arrays!
        final int[] nmax = {0};
        text.chars().forEach(ch -> {
            if(ch == '\n'){
                if(n[0] > nmax[0]) nmax[0] = n[0];
                n[0] = 0;
                line_n ++;
            } else
                n[0]++;
        });
        if(nmax[0] == 0)
            line_max = text.length();
        else
            line_max = nmax[0];
    }

    public Text(String text, Vector position){
        this(text, (int) position.getX(), (int) position.getY());
    }

    //    public TextFast update(){ return this; }
    public Text setSpaceBetweenChars(int space){
        cs = space; return this;
    }
    public Text setCenteredX(boolean centered){
        shouldCenter = centered; return this;
    }
    public Text setAbsolute(boolean absolute) {
        isAbsolute = absolute;
        return this;
    }
    public Text setCenteredY(boolean centered){
        shouldCenterY = centered; return this;
    }
    public Text setSize(int w, int h){
        cw = w; ch = h;
        return this;
    }
    public Text setPosition(int x, int y){
        this.x = x; this.y = y;
        return this;
    }
    public Text setPosition(Vector pos){
        this.x = (int) pos.getX(); this.y = (int) pos.getY();
        return this;
    }
    public Vector getPosition(){
        return new Vector(x, y);
    }

    public void draw(){
        int _y = y;
        if(shouldCenterY) _y -= (line_n * ch) - ch / 2; // technically incorrect, but I don't plan on doing this with multiple lines.
        int index = 0;
        for(int i = 0; i < text.length(); i ++){

            if(text.charAt(i) == '\r') continue;
            if(text.charAt(i) == '\n') {
                index = 0;
                _y += ch;
                continue;
            }

            final int charCode = (int) text.charAt(i) - 32;
            double
                    tx = (charCode % 16 / 16f), // texture x
                    ty = (Math.floor(charCode / 16f) / 16f); // texture y

            int _x = x + index * cs;
            if(shouldCenter) _x -= line_max / 2 * cs;
            Shapes.character(_x, _y, cw, ch, tx, ty, 0.0625, 0.0625, id, isAbsolute);
            index ++;
        }
    }

    public Text undraw(){
        int _y = y;
        if(shouldCenterY) _y -= 8;
        GL11.glColor3f(0.05f, 0.05f, 0.05f);
        int _w = (cs) * text.length();
        if(!shouldCenter)
            Shapes.rect(new Vector(x,             _y), new Vector(_w, 16));
        else
            Shapes.rect(new Vector(x - _w / 2, _y), new Vector(_w, 16));
        GL11.glColor3f(1f, 1f, 1f);
        return this;
    }

}

// TEXT TEST:

//            Color.COLOR_WHITE.apply();
//            new Text("Something!\nSomething!\nSomething!", Window.getWidth() / 2, Window.getHeight() / 2)
//                    .setSize(12, 12)
//                    .setCenteredX(true).setCenteredY(true)
//                    .setSpaceBetweenChars(64)
//                    .setPosition((int) Window.getFrameCount() % Window.getWidth(), Window.getHeight() / 2)
//                    .draw();
//            Color.COLOR_RED.apply();
////            Shapes.ellipse(Window.getDimensions().mul(0.5f), 2, 10);
//            Shapes.rect(new Vector(0, Window.getHeight() / 2 - 2), new Vector(Window.getWidth(), 4));
//            Shapes.rect(new Vector(Window.getWidth() / 2 - 2, 0), new Vector(4, Window.getHeight()));