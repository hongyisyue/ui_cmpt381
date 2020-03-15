package com.example.homeyxue.graphdemo;

/**
 * Created by homeyxue on 2018-02-06.
 */
public class Vertex {
    float x, y;
    float radius;
    int id;

    public Vertex(float newX, float newY, int newID) {
        x = newX;
        y = newY;
        id = newID;
        radius = 0.025f;
    }

    public boolean contains(float sx, float sy) {
        return GraphModel.dist(x,y,sx,sy) <= radius;
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }
}

