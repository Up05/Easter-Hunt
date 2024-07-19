package me.Ult1;

import me.Ult1.Level.Level;
import me.Ult1.Weapons.Image;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.IntBuffer;
import java.util.LinkedList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    private Main(){}
    private static Main instance = new Main();

    private long window;
    public static String DATA_PATH;

    private Player player;
    private LinkedList<Entity> entities = new LinkedList<>();
    private Level currentLevel;

    public static final double ANGLE_COEF = 180 / Math.PI;

    public void run() throws IOException {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = glfwCreateWindow(Window.getWidth(), Window.getHeight(), "Easter Hunt", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glOrtho(0, Window.getWidth(), Window.getHeight(), 0, 1.0, -1.0); // p5 proj mat thing yeap

    }

    private void loop() {

        // should put this in the config, as a toggle-able thing
        if (glfwRawMouseMotionSupported())
            glfwSetInputMode(window, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);

        Image.init();
        Text.init();
        KeyBinds.init();

//        LevelLoader levelLoader = new LevelLoader("level0");
        currentLevel = new Level("level0");

        player = new Player(Window.getSize().div(2), new Vector(45, 45));

        Window.setBackground_color(new Color(20, 20, 20, 255));

        int tps = (int) Config.config.get("ticks_per_second");
        long timePassed = 0, deltaTime = 0;
        long timeAtStart = System.currentTimeMillis();

        while ( !glfwWindowShouldClose(window) ) {

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            {
                IntBuffer pWidth  = BufferUtils.createIntBuffer(1); // int* pWidth
                IntBuffer pHeight = BufferUtils.createIntBuffer(1); // int*
                glfwGetWindowSize(window, pWidth, pHeight);
                if(Window.getWidth() != pWidth.get(0)) {
                    Vector pastDimensions = Window.getSize();
                    Window.setWidth(pWidth.get(0));
                    Window.setHeight(pHeight.get(0));
                    glViewport(0, 0, Window.getWidth(), Window.getHeight());
                    Window.onResize(pastDimensions, Window.getSize());
                    glLoadIdentity();
                    glOrtho(0, Window.getWidth(), Window.getHeight(), 0, 1.0, -1.0);
                    glMatrixMode(GL11.GL_MODELVIEW);
                }
            }

            currentLevel.draw();

//            Image.get(Image.SAND).draw(Window.getSize().div(2).relative().absolute(), 0, false);

            Color.COLOR_WHITE.apply();

            int i = 0;
            while(timePassed >= 1000 / tps && i < 20) {
                KeyBinds.poll();
                player.update();
                i ++;
                timePassed -= 1000 / tps;
            }
            player.show(timePassed / (1000d / tps));


            deltaTime = System.currentTimeMillis() - timeAtStart;
            timeAtStart = System.currentTimeMillis();
            timePassed += deltaTime;

            Window.incrementFrameCount();
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
//        DATA_PATH = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();

        AppDirs appDirs = AppDirsFactory.getInstance();
        DATA_PATH = appDirs.getUserDataDir("EasterHunt", null, "Ult1");
        System.out.println("Data path: " + DATA_PATH);
        new File(DATA_PATH).mkdirs();

        Config.init();
        SpriteHandler.loadAll();

        instance.run(); // ~ return 0
    }


    public long getWindow() {
        return window;
    }

    public static Main getInstance(){
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }
}

/*

    Jesus hunts down Romans to Soviets with a BAZOOKA
    When he respawns spawn a bunch of Easter-y particles & sh*t

    Todo:
     - Camera is very jittery and I have no idea how to solve it, since I, currently, ONLY move the world. The player is in the middle, but it'd, probably, be better to move player a bit too...

    Make everything do everything from 1 point, not whereever they feel like.

 */
