package me.Ult1.Weapons;

import me.Ult1.Level.Level;
import me.Ult1.Main;
import me.Ult1.Player;
import me.Ult1.Shapes;
import me.Ult1.Vector;

public class Rocket extends Shot {

    public Rocket(Vector position, Vector direction) {
        super(position, direction.mul(15), new Vector(0, 0));
    }

    @Override
    public boolean update() {


        if(position.relative().isOffscreen())
            return true;
        if(!Level.isAir(Main.getInstance().getCurrentLevel().getTileAt(position))) {
            onContact();
            return true;
        }


        velocity = velocity.add(acceleration);
        position = position.add(velocity);

        return false;
    }

    @Override
    public void show() {
//        Shapes.ellipse(position, 10, 3);
        Image.get(Image.SAND).draw(position, 0, true, 45);

    }

    @Override
    protected void onContact() {

//        System.out.println(Main.getInstance().getPlayer().getPosition().dist(position));
//        System.out.println(Math.sqrt(1 / Main.getInstance().getPlayer().getPosition().dist(position)));
        Player player = Main.getInstance().getPlayer();
        Vector centeredPosition = player.getPosition().add(player.getSize().div(2));
        if(centeredPosition.dist(position) < 200) {
            Main.getInstance().getPlayer().setAcceleration(
                player.getAcceleration().add(
                    centeredPosition.sub(position).mul(
                             750f / (float) Math.pow(player.getPosition().dist(position), 2f)
                    )));
        }



    }
}
