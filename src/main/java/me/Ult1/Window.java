package me.Ult1;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.opengl.GL11.glClearColor;
public class Window {

    private static int width = 1280, height = 720;
//    private static int width = 1920, height = 1080 - 48 - 24;
    private static int scaleX = 16 * 3, scaleY = 16 * 3;
    private static Vector camera = new Vector(0, 0);
    private static final Vector initialSize = new Vector(width, height);

    public static void setWidth(int _width) {
        width = _width;
    }
    public static int getWidth() {
        return width;
    }
    public static void setHeight(int _height) {
        height = _height;
    }
    public static int getHeight() {
        return height;
    }
    public static Vector getSize(){
        return new Vector(width, height);
    }
    public static Vector getInitialSize() {
        return initialSize;
    }
    public static int getScaleX() {
        return scaleX;
    }
    public static int getScaleY() {
        return scaleY;
    }

    public static Vector getCameraPosition() {
        return camera;
    }
    public static void setCameraPos(Vector position) {
        camera = position;
    }

    public static void onResize(Vector previous, Vector current){
//        Main.getInstance().getPlayer().getPosition().setY(
//                Main.getInstance().getPlayer().getPosition().getY() -
//                current.sub(previous).getY()
//        );
        scaleX *= previous.getX() / current.getX();
        scaleY *= previous.getY() / current.getY();
    }

    private static Color backgroundColor;

    public static void setBackground_color(Color color) {
        Window.backgroundColor = color;
        glClearColor((color.r) / 255f, (color.g) / 255f, (color.b) / 255f, 1f);
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static boolean imagesEnabled = false;

    private static long frameCount = 0;

    public static long getFrameCount() {
        return frameCount;
    }
    public static void incrementFrameCount(){
        frameCount ++;
    }

    public static Vector getMousePosition(boolean relative){
        DoubleBuffer
            x = BufferUtils.createDoubleBuffer(1),
            y = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(Main.getInstance().getWindow(), x, y);
        Vector vector = new Vector(x.get(0), y.get(0));
        if(relative)
            vector = vector.sub(Window.getCameraPosition()).add(Window.getSize().div(2));
        return vector;
    }
}
