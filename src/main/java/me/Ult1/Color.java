package me.Ult1;

import static org.lwjgl.opengl.GL11.glColor4f;

public class Color {

    public static Color current;

    public int r;
    public int g;
    public int b;
    public int a;

    public Color(){}

    public Color(int r, int g, int b, int a){
//        this.r = (byte) r;
//        this.g = (byte) g;
//        this.b = (byte) b;
//        this.a = (byte) a;

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color brightness(int percent){
        Color color = new Color(r, g, b, a);
        color.r = (int) (color.r * (percent / 100f));
        color.g = (int) (color.g * (percent / 100f));
        color.b = (int) (color.b * (percent / 100f));
        return color;
//        return this;
    }

    public void apply(){
//        glColor4i(r, g, b, a);
//        System.out.printf("r: %d, g: %d, b: %d, a: %d\n", r, g, b, a);
        glColor4f(r / 256f, g / 256f, b / 256f, a / 256f);
        current = this;
    }

    public static Color
        COLOR_BLACK      = new Color(   0,    0,   0, 256),
        COLOR_WHITE      = new Color( 256,  256, 256, 256),
        COLOR_GRAY       = new Color( 128,  128, 128, 256),
        COLOR_RED        = new Color( 256,    0,   0, 256),
        COLOR_GREEN      = new Color(   0,  256,   0, 256),
        COLOR_BLUE       = new Color(   0,    0, 256, 256),
        COLOR_AQUAMARINE = new Color(   0,  256, 135, 256),
        COLOR_BROWN      = new Color(  80,   40,   0, 250);

}
