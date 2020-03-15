package sample;

/**
 * Created by homeyxue on 2018-02-06.
 */
public class Vertex {
    double x,y;
    double radius;
    int id;

    public Vertex(double newX, double newY, int newId){
        x = newX;
        y = newY;
        id = newId;
        radius = 15;
    }

    public void move(double moveX, double moveY){
        x += moveX;
        y += moveY;
    }

    public boolean contains(double targetX, double targetY){
        return Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2)) <= radius;
    }

}
