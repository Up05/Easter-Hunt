package me.Ult1;


import org.yaml.snakeyaml.Yaml;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Config {

    static File file = null;
    static Yaml yaml = null;
    static HashMap<String, Object> config = new HashMap<>(20);

    public static void init() throws FileNotFoundException {
        file = new File(Main.DATA_PATH + "\\config.cfg");
        yaml = new Yaml();
        config = yaml.load(new FileReader(file));

        System.out.println(config.get("keybinds"));
        KeyBinds.keyBinds = (Map<String, Integer>) config.get("keybinds");

    }


}
