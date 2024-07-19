package me.Ult1;

import me.Ult1.Level.Level;

abstract public class Entity {

    protected int health;
    protected Vector position, velocity = new Vector(0, 0), acceleration = new Vector(0, 0), size;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public Vector getPosition() {
        return position;
    }
    public Vector getVelocity() {
        return velocity;
    }
    public Vector getAcceleration() {
        return acceleration;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public Vector getSize() {
        return size;
    }

    /**
     * CollisionData#getDirection() gives <b>REVERSED</b> direction! <br>
     * IT POINTS FROM COLLISION POINT TO THIS ENTITY!
     * please use Vector#mul(-1) to reverse it, if needed. <br>
     */
    protected CollisionData isCollidingWithGround(){
        CollisionData data = new CollisionData();
        Vector corners[] = {
                position,
                position.add(size.getX(), 0, 0),
                position.add(0, size.getY(), 0),
                position.add(size)
        };

        Vector avgCollisionPoint = null;

        for(int i = 0; i < 4; i ++)
            if(!Level.isAir(Main.getInstance().getCurrentLevel().getTileAt(corners[i]))) {
                data.setColliding(true);

                if(avgCollisionPoint == null)
                    avgCollisionPoint = corners[i];
                else
                    avgCollisionPoint = avgCollisionPoint.midpoint(corners[i]);
            }

        if(!data.iscolliding())
            return data;

        data.setDirection(
                position.add(size.div(2)).sub(avgCollisionPoint).normalize() // no avgCollisionPoint cannot be null.
        );

        return data;
    } // there might be a better way to do this, but I don't really care, it's like 3 extra lines...

    protected boolean isOnGround(){
        Level cl = Main.getInstance().getCurrentLevel(); // current level
        Vector os = position.add(0, velocity.getY() + 1, 0); // offset


        return
            cl.getTileAt(os.add(size))                             != '0' ||
            cl.getTileAt(os.clone().setY(os.getY() + size.getY())) != '0';

    }
}
