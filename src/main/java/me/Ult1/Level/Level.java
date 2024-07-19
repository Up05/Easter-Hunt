package me.Ult1.Level;

import me.Ult1.*;
import me.Ult1.Weapons.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Level {

    private String name;
    private char[][] tiles;
    private Collection<Entity> entities = new ArrayList<>(20);

    /** @deprecated the array object already has length in java: tiles.length & tiles[0].length*/
    private int width, height;
    private int style;

    private char[][] tiles_bg;

    public Level(String name){
        this.name = name;
        try {
            LevelLoader loader = new LevelLoader(name);
            width = loader.width; height = loader.height;  style = loader.style;

            tiles = new char[loader.width][loader.height];
            tiles_bg = new char[loader.width_bg][loader.height_bg];

            for(int x = 0; x < width; x ++)
                for(int y = 0; y < height; y ++) {
                    int i = y * width + x;
                    tiles[x][y] = loader.data.get(i);
                }

            for(int x = 0; x < loader.width_bg; x ++)
                for(int y = 0; y < loader.height_bg; y ++) {
                    int i = y * loader.width_bg + x;
                    tiles_bg[x][y] = loader.data_bg.get(i);
                }
            loader.data_bg.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int x = 0; x < width; x ++)
            for(int y = 0; y < height; y ++){
                if(tiles[x][y] >= 'A'){
                    switch(tiles[x][y]){
                        case 'A':
                            // entities.add(
                            // new EntityChild(new Vector(x * Window.scaleX, y * Window.scaleY)),
                            // new Vector(Config.getEntitySize('A').x * Window.scale, Config.getEntitySize('A').y * Window.scale)
                            // )
                            break;



                        default:
                            System.err.println("No entity of that type exists! (at least in the Level#constructor#switch clause)");
                    }
                }
            }
    }


    public void draw(){
        // Fixme: TEMPORARY

        int yOffset = 0;
            yOffset =  height - tiles_bg[0].length;

        for(int x = 0; x < tiles_bg.length; x ++)
            for(int y = yOffset; y < tiles_bg[0].length + yOffset; y ++) {
//            for(int y = tiles_bg[0].length - 1; y > -1; y --){

                int _x = x * Window.getScaleX(),
                    _y = y * Window.getScaleY();
                switch(tiles_bg[x][y - yOffset]){
//                    case '0': Color.COLOR_WHITE.apply(); break;
                    case '1': Color.COLOR_GREEN.brightness(50).apply(); break;
                    case '2': Color.COLOR_BROWN.brightness(50).apply(); break;
                    case '3': Color.COLOR_BLUE .brightness(50).apply(); break;
                    default : continue;
                }
                if(tiles_bg[x][y - yOffset] != '0'){
                    Shapes.rect(new Vector(_x, _y), new Vector(Window.getScaleX(), Window.getScaleY()));
                }

//                if(tiles_bg[x][y - yOffset] == '1')
//                    Image.get(Image.SAND).draw(new Vector(_x, _y), 0, true, 0);

            }


        for(int x = 0; x < width; x ++)
            for(int y = 0; y < height; y ++){ // it doesn't matter how you loop through a list here...
                int _x = x * Window.getScaleX(),
                    _y = y * Window.getScaleY();

                switch(tiles[x][y]){
//                    case '0': Color.COLOR_WHITE.apply(); break;
                    case '1': Color.COLOR_GREEN.apply(); break;
                    case '2': Color.COLOR_BROWN.apply(); break;
                    case '3': Color.COLOR_BLUE .apply(); break;
                    default : continue;
                }
                if(tiles[x][y] != '0')
                    Shapes.rect(new Vector(_x, _y), new Vector(Window.getScaleX(), Window.getScaleY()));
            }
    }



    public char getTile(int x, int y){
        if(x < 0 || x >= width || y < 0 || y >= height) {
//            System.err.println("Tile at: " + x + ", " + y + " is inaccessible! (map width & height: " + width + ", " + height + ')');
            return '0';
        }
        return tiles[x][y];
    }

    public char getTileAt(Vector position){ // ! I gotta center everything to the curr mid of screen, I guess...
        Vector p = position.clone();
//        p.setY(p.getY() + height * Window.getScaleY());
        p.setY(p.getY() + Window.getSize().sub(Window.getInitialSize()).getY());

        // CHANGE

        p.setX((float) Math.floor(                      p.getX() / Window.getScaleX()));
        p.setY((float) Math.floor((Window.getInitialSize().getY() - p.getY()) / Window.getScaleY())); // why height - y; and         height - y ln: 125

        return getTile((int) p.getX(), height - 1 - (int) p.getY());

//        p.setX((float) Math.floor( p.getX() / Window.getScaleX()));
//        p.setY((float) Math.floor( p.getY() / Window.getScaleY())); // why height - y; and         height - y ln: 125
//
//        return getTile((int) p.getX(), (int) p.getY());
    }

    public boolean isAir(Vector position){
        char tile = getTileAt(position);
        return tile == '0' || tile == 0;
    }

    public static boolean isAir(char tile){
        return tile == '0' || tile == 0;
    }

    public boolean setTileAt(Vector position, char tile){
        Vector p = position.clone();
//        p = p.add(Main.getInstance().getPlayer().getPosition().mul(-1));
        p.setX((float) Math.floor(p.getX() / Window.getScaleX()));

        // TODO: ?  p.setY(Window.getSize().sub(Window.getInitialSize()).getY() / 2);


        if(p.getX() < 0 || p.getX() >= width || p.getY() < 0 || p.getY() >= height) {
            return false;
        }

        tiles[(int) p.getX()][height - (int) p.getY()] = tile;
        return true;
    }

    public int getHeight() {
        return height;
    }
}
