/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobmvcdemo;

/**
 *
 * @author gutwin
 */
public class Blob {
    double x;
    double y;
    int zOrder;
    double r;
    
    public Blob(double newX, double newY, double newR, int newZ) {
        x = newX;
        y = newY;
        r = newR;
        zOrder = newZ;
    }
    
    public boolean contains(double sx, double sy) {
        return r > Math.sqrt(Math.pow(sx - x, 2) + Math.pow(sy - y, 2));
    }
}
