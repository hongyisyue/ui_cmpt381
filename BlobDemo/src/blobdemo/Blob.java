/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by homeyxue on 2018-03-23.
 */
public class Blob implements Groupable{

    double x;
    double y;
    int zOrder;
    double r;
    int size;

    public Blob(double newX, double newY, double newR, int newZ) {
        x = newX;
        y = newY;
        r = newR;
        zOrder = newZ;
        size = 1;
    }

    public Blob(Groupable aBold){
        r = (aBold.getRight()-aBold.getLeft())/2;
        x = aBold.getLeft()+r;
        y = aBold.getBottom()+r;
        size = 1;
    }

    public boolean contains(double sx, double sy) {
        return r > Math.sqrt(Math.pow(sx - x, 2) + Math.pow(sy - y, 2));
    }

    public boolean hasChildren(){return false;}

    public boolean isContained(double x1, double y1, double x2, double y2) {
        return x-r >= x1 && y-r >= y1 && x+r <= x2 && y+r <= y2;
    }

    public void drawGroup(GraphicsContext gc){
        gc.fillOval(x-r, y-r, r*2, r*2);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x-r, y-r, r*2, r*2);
        //gc.strokeRect(x-r, y-r, r*2, r*2);
    }

    public double getLeft(){
        return x-r;
    }

    public double getRight(){
        return x+r;
    }

    public double getTop(){
        return y-r;
    }

    public double getBottom(){
        return y+r;
    }

    @Override
    public void setZ(int newZ) {
        zOrder = newZ;
    }

    @Override
    public int getZ() {
        return zOrder;
    }

    @Override
    public void setSize(int newSize) {
        size = newSize;
    }

    @Override
    public int getSize() {
        return size;
    }

    public ArrayList<Groupable> getChildren(){
        return null;
    }

    public void moveGroup(double dx, double dy) {
        x += dx;
        y += dy;
    }
}
