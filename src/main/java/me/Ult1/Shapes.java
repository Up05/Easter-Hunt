package me.Ult1;

import static org.lwjgl.opengl.GL11.*;
public class Shapes {

    public static void rect(Vector position, Vector size){

//        pos = pos.clone();
//        pos = pos.sub(Window.getCameraPosition()).add(Window.getInitialSize().div(2).getX(), Window.getInitialSize().getY() / 2, 0);
//        pos.setY(pos.getY() - Window.getSize().sub(Window.getInitialSize()).getY());
        Vector pos = position;
        pos = pos.relative();

        glBegin(GL_TRIANGLE_STRIP);
        pos.add(0, size.getY(), 0).vertex();
        pos.vertex();
        pos.add(size).vertex();
        pos.add(size.getX(), 0, 0).vertex();
        glEnd();
    }

    public static void rectAbsolute(Vector pos, Vector size){ // { position: absolute; }
        glBegin(GL_TRIANGLE_STRIP);
        pos.add(0, size.getY(), 0).vertex();
        pos.vertex();
        pos.add(size).vertex();
        pos.add(size.getX(), 0, 0).vertex();
        glEnd();
    }

    public static void ellipse(Vector pos, int radius, int resolution){

        pos = pos.sub(Window.getCameraPosition()).add(Window.getSize().div(2));

        final int vertN = resolution + 1;
        final double TWO_PI = Math.PI * 2d;

        Integer[] verts = new Integer[vertN * 2];

        for (int i = 1; i < vertN; i ++){
            verts[i * 2]     = (int) Math.round(pos.getX() + ( radius * Math.cos( i * TWO_PI / resolution ) ));
            verts[i * 2 + 1] = (int) Math.round(pos.getY() + ( radius * Math.sin( i * TWO_PI / resolution ) ));
        }

        glPushMatrix();
        glBegin(GL_TRIANGLE_FAN);
        for(int i = 0; i < verts.length; i += 2)
            if(verts[i] != null && verts[i + 1] != null)
                glVertex2d(verts[i], verts[i + 1]);
        glEnd();
        glPopMatrix();

    }
    // TODO: RELATIVE COORDS RESIZE!!!
    public static void character(int x, int y, int w, int h, double tx, double ty, double tw, double th, int textureId, boolean isAbsolute){
        if(!Window.imagesEnabled){
            glEnable(GL_TEXTURE_2D);
            Window.imagesEnabled = true;
        }

        if(!isAbsolute) {
            Vector temp = new Vector(x, y);
            temp = temp.relative();
            x = (int) temp.getX();
            y = (int) temp.getY();
        }

        final int x2 = x + w, y2 = y + h;
        final double tx2 = tx + tw, ty2 = ty + th;

        glBindTexture(GL_TEXTURE_2D, textureId);
        glBegin(GL_QUADS);
        glTexCoord2d(tx, ty);
        glVertex2i(x , y );
        glTexCoord2d(tx2, ty);
        glVertex2i(x2, y );
        glTexCoord2d(tx2, ty2);
        glVertex2i(x2, y2);
        glTexCoord2d(tx, ty2);
        glVertex2i(x , y2);
        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);
    }


}
