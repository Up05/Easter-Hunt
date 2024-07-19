package me.Ult1;

import me.Ult1.Weapons.RocketLauncher;
import me.Ult1.Weapons.Weapon;

public class Player extends Entity {

    public static final byte
            ACTION_NONE = 0,
            ACTION_MOVE_LEFT = 1,
            ACTION_MOVE_RIGHT = 2,
            ACTION_MOVE_JUMP = 3,
            ACTION_SHOOT = 4;

    boolean interpolation = true;

    Weapon activeWeapon = new RocketLauncher();

    public Player(Vector pos, Vector size){
        this.position = pos;
        this.size = size;
        this.acceleration.setY(-4);
        Window.setCameraPos(position);
        interpolation = (boolean) Config.config.get("interpolation");
    }

    public void doAction(byte action){
        switch(action) {
            case ACTION_MOVE_LEFT:
                if(acceleration.getX() > -0.8)
                    acceleration = acceleration.add(-0.525f, 0, 0);
                break;
            case ACTION_MOVE_RIGHT:
                if(acceleration.getX() < 0.8)
                    acceleration = acceleration.add(0.525f, 0, 0);
                break;
            case ACTION_MOVE_JUMP:
//                System.out.println(isOnGround());
                if(isOnGround()) {
                    position.setY(position.getY() - 2);
                    acceleration = acceleration.add(0, -4.0f, 0);
                }
                break;
            case ACTION_SHOOT:
                activeWeapon.shoot(position.add(size.div(2)));
        }


    }

    public void update(){
        CollisionData collisionData = isCollidingWithGround();
        if(collisionData.iscolliding()) {
            position = position.add(velocity.getX() * -2.2f, 0, 0);
            velocity = collisionData.getDirection().mul(velocity.hypot()).mul(0.5f);
            velocity.setX(velocity.getX() / 10);
            acceleration = collisionData.getDirection().mul(acceleration.hypot()).mul(0.5f);
        }

        if(isOnGround()){
            acceleration.setY(0);
            acceleration.mul(0.9f);
            velocity.setY(0);
        } else
            acceleration = acceleration.add(0, 0.05f, 0);

        activeWeapon.update();

        acceleration = acceleration.mul(0.90f);
        velocity = velocity.mul(0.85f);

        velocity = velocity.add(acceleration);
        position = position.add(velocity);

//        Window.getCameraPosition().add(Window.getCameraPosition().sub(position));

//        Window.setCameraPos(Window.getCameraPosition().add(velocity))


    }

    public void show(double lerpAmount){
        Color.COLOR_AQUAMARINE.apply();
        if(Window.getCameraPosition().dist(position) > 20)
            Window.setCameraPos(Window.getCameraPosition().add( position.sub(Window.getCameraPosition()).div(20) ));


        Vector interpolatedPosition = position.lerp(position.add(velocity), lerpAmount);
        Shapes.rectAbsolute(Window.getInitialSize().div(2).add(interpolatedPosition.sub(Window.getCameraPosition())), size);

        activeWeapon.show();


        new Text(((int) position.getX()) + "; " + ((int) position.getY()), 24, 32)
                .draw();

//        Shapes.rectAbsolute(Window.getDimensions().div(2), size);
        Color.COLOR_WHITE.apply();
    }
}
