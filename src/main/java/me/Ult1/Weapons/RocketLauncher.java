package me.Ult1.Weapons;

import me.Ult1.Main;
import me.Ult1.Player;
import me.Ult1.Vector;
import me.Ult1.Window;

import java.util.LinkedList;

public class RocketLauncher extends Weapon {
    LinkedList<Rocket> rockets = new LinkedList<>();

    @Override
    public Weapon shoot(Vector position) {

        Vector direction = Window.getMousePosition(false).sub(Window.getSize().div(2)).normalize();


        rockets.add(new Rocket(position.sub(direction.mul(6)), direction));



        return this;
    }

    @Override
    public Weapon update() {
        rockets.removeIf(Rocket::update);
        return this;
    }

    @Override
    public Weapon show() {
        for(Rocket rocket : rockets)
            rocket.show();

        Player player = Main.getInstance().getPlayer();
        Vector facing = Window.getMousePosition(false).sub(player.getPosition().absolute());
        double angle = Math.atan2(facing.getY(), facing.getX()) * Main.ANGLE_COEF;

        Image.get(Image.ROCKET_LAUNCHER).draw(player.getPosition().add(player.getSize().div(2)), 0, true, (int) angle);

        return this;
    }
}
