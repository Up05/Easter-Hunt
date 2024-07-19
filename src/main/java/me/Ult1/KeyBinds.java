package me.Ult1;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBinds {

    public static Map<String, Integer> keyBinds; // gets set in Config#init()

    public static void onInput(int key, boolean mouse, int action, int mods){
        // if GUIIsOpen;
        // else;
//        System.out.println("action: " + action + "(" + GLFW_PRESS + ")");




//        if(key == keyBinds.get("move_left")){ // switch statements? Lol!
//            if(action == GLFW_PRESS) {
//                System.out.println(key + ", " + keyBinds.get("move_left"));
//                Main.player.setAction(Player.ACTION_MOVE_LEFT);
//            }
//            else if(action == GLFW_RELEASE)
//                Main.player.setAction(Player.ACTION_NONE);
//        } else if(key == keyBinds.get("move_right")){
//            if(action == GLFW_PRESS)
//                Main.player.setAction(Player.ACTION_MOVE_RIGHT);
//            else if(action == GLFW_RELEASE)
//                Main.player.setAction(Player.ACTION_NONE);
//        }

    }

    public static void init(){

        glfwSetMouseButtonCallback(Main.getInstance().getWindow(), (window, button, action, mods) -> {
            if(action == GLFW_RELEASE) {
                if (keyBinds.getOrDefault("shoot", 0) == button)
                    Main.getInstance().getPlayer().doAction(Player.ACTION_SHOOT);

            }
        });

//        if(glfwGetMouseButton(Main.getInstance().getWindow(), keyBinds.getOrDefault("shoot", 0)) == GLFW_PRESS)
//            Main.getInstance().getPlayer().doAction(Player.ACTION_SHOOT);
    }

    public static void poll(){

        if(glfwGetKey(Main.getInstance().getWindow(), keyBinds.getOrDefault("move_left", 65)) == GLFW_PRESS)
            Main.getInstance().getPlayer().doAction(Player.ACTION_MOVE_LEFT);

        if(glfwGetKey(Main.getInstance().getWindow(), keyBinds.getOrDefault("move_right", 68)) == GLFW_PRESS)
            Main.getInstance().getPlayer().doAction(Player.ACTION_MOVE_RIGHT);

        if(glfwGetKey(Main.getInstance().getWindow(), keyBinds.getOrDefault("move_jump", 32)) == GLFW_PRESS)
            Main.getInstance().getPlayer().doAction(Player.ACTION_MOVE_JUMP);



    }


}


/*
const char* key_name = glfwGetKeyName(GLFW_KEY_W, 0);
show_tutorial_hint("Press %s to move forward", key_name);


 */
