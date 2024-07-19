package me.Ult1;

public class CollisionData {
    private boolean isColliding;
    private Vector direction;

    public CollisionData(){
        isColliding = false;
        direction = new Vector();
    }

    public boolean iscolliding(){
        return isColliding;
    }

    /**
     * By default, the given direction is reversed! <br>
     * It points from averaged <b>collision point to player!</b>
     */
    public Vector getDirection() {
        return direction;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }
}
