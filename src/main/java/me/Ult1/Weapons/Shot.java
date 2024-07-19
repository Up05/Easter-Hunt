package me.Ult1.Weapons;

import me.Ult1.Vector;

public abstract class Shot { // I made this and then realized it's a slightly better version of Entity... Taking breaks is the worst!
    protected Vector position, velocity, acceleration;

    public Shot(Vector position, Vector velocity, Vector acceleration){
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public abstract boolean update();

    public abstract void show();

    protected abstract void onContact();

    public Vector getPosition() {
        return position;
    }
    public Vector getVelocity() {
        return velocity;
    }
    public Vector getAcceleration() {
        return acceleration;
    }
    public void setPosition(Vector position) {
        this.position = position;
    }
    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
}
