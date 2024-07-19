package me.Ult1;

import org.lwjgl.opengl.GL11;

public class Vector {
    protected float x, y, z;

    public Vector(){}
    public Vector(int x, int y, int z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }
    public Vector(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }
    public Vector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Vector(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }
    public Vector(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    /** @param angle angle in radians */
    public static Vector fromPolar(float radius, double angle){
        return new Vector(Math.cos(angle) * radius, Math.sin(angle) * radius);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }

    public Vector setX(float x) {
        this.x = x;
        return this;
    }
    public Vector setY(float y) {
        this.y = y;
        return this;
    }
    public Vector setZ(float z) {
        this.z = z;
        return this;
    }

    public Vector set(Vector vector){
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
        return this;
    }

    public Vector clone(){
        return new Vector(this.x, this.y, this.z);
    }

    public Vector add(float x, float y, float z) {
        return new Vector(this.x + x, this.y + y, this.z + z);
    }
    public Vector sub(float x, float y, float z) {
        return new Vector(this.x - x, this.y - y, this.z - z);
    }
    public Vector mul(float x, float y, float z) {
        return new Vector(this.x * x, this.y * y, this.z * z);
    }
    public Vector mul(float s) {
        return new Vector(this.x * s, this.y * s, this.z * s);
    }
    public Vector div(float x, float y, float z) {
        return new Vector(this.x / x, this.y / y, this.z / z);
    }
    public Vector div(float s) {
        return new Vector(this.x / s, this.y / s, this.z / s);
    }

    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }
    public Vector sub(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }
    public Vector mul(Vector vector) {
        return new Vector(this.x * vector.x, this.y * vector.y, this.z * vector.z);
    }
    public Vector div(Vector vector) {
        return new Vector(this.x / vector.x, this.y / vector.y, this.z / vector.z);
    }

    public float dist(Vector vector){
        return (float) Math.sqrt(
            Math.pow(this.x - vector.x, 2) +
            Math.pow(this.y - vector.y, 2) +
            Math.pow(this.z - vector.z, 2)
        );
    }


    /** Gets the hypotenuse/Pythagoras theorem's value from the current vector <br> <code>c = sqrt(a^2 + b^2 + c^2)</code> */
    public float hypot(){
        return (float) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
    }

    /** @implNote  THIS DOESN'T WORK FOR POINTS! (Position, Window width & height...) */
    public Vector normalize(){
        float c = hypot();
        return new Vector(this.x / c, this.y / c, this.z / c);
    }

    public Vector midpoint(Vector vector){
        return new Vector((this.x + vector.x) / 2, (this.y + vector.y) / 2, (this.z + vector.z) / 2);
    }

    public Vector lerp(Vector vector, double pct){
        return new Vector(this.x + pct * (vector.x - this.x), this.y + pct * (vector.y - this.y), this.z + pct * (vector.z - this.z));
    }

    /** @implNote  THIS DOESN'T WORK FOR POINTS! (Position, Window width & height...) */
    public Vector getWithMagnitude(float magnitude){
        this.normalize().mul(magnitude);
        return this;
    }

    public static boolean help_me_god_please = true;

    /** Mainly for drawing, it deals with camera, resizing & so on...*/
    public Vector relative(){
        Vector vector = this.clone();
        // pos   -= camera + window_init/2,
        // pos.y -= window + window_init

        vector = vector.sub(Window.getCameraPosition()).add(Window.getInitialSize().div(2));
        vector.setY(vector.getY() - (Window.getHeight() - Window.getInitialSize().getY()));
        return vector;
    }

    public Vector absolute(){
        Vector vector = this.clone();

        vector.setY(vector.getY() + Window.getHeight() - Window.getInitialSize().getY());
        vector = vector.add(Window.getCameraPosition()).sub(Window.getInitialSize().div(2));

        help_me_god_please = false;

//        vector = vector.sub(Window.getCameraPosition()).add(Window.getInitialSize().div(2));
//        vector.setY(vector.getY() - Window.getSize().sub(Window.getInitialSize()).getY());
        return vector;

    }

    public boolean isOffscreen(){
        return x > Window.getWidth() || y > Window.getHeight() || x < 0 || y < 0;
    }

    public Vector vertex(){
        GL11.glVertex2d(x, y);
        return this;
    }

    public Vector print(){
        System.out.println("x: " + x + ", y: " + y);
        return this;
    }

    @Override
    public String toString() {
        return "Vector (" + Math.floor(x * 100) / 100f + "; " + Math.floor(y * 100) / 100f + "; " + Math.floor(z * 100) / 100f + ')';
    }
}
