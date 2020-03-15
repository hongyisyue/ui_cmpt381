/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Group implements Groupable, Serializable{

    ArrayList<Groupable> groups;
    double left, top, right, bottom;

    public Group(){
        groups = new ArrayList<>();
        left = Double.MAX_VALUE;
        top = Double.MAX_VALUE;
        right = 0;
        bottom = 0;
    }

    public Group(Groupable aGroup){
        this();
        if(aGroup.hasChildren()){
            for(Groupable g: aGroup.getChildren()){
                Group ng = new Group(g);
                add(ng);
            }
        } else {
            Blob nb = new Blob(aGroup);
            add(nb);
        }
    }
    public void add(Groupable g) {
        groups.add(g);
        left = Math.min(g.getLeft(), left);
        top = Math.min(g.getTop(), top);
        right = Math.max(g.getRight(), right);
        bottom = Math.max(g.getBottom(), bottom);
    }

    public void remove(Groupable g) {
        groups.remove(g);
    }

    public boolean hasChildren() {
        return true;
    }

    public boolean contains(double x, double y) {
        boolean found = false;
        for (Groupable g : groups) {
            if (g.contains(x, y)) {
                found = true;
            }
        }
        return found;
    }

    public boolean isContained(double x1, double y1, double x2, double y2) {
        boolean allIn = true;
        for (Groupable g : groups) {
            if (!g.isContained(x1, y1, x2, y2)) {
                allIn = false;
            }
        }
        return allIn;
    }

    public void drawGroup(GraphicsContext gc) {
        for (Groupable g : groups) {
            g.drawGroup(gc);
        }
        gc.setStroke(Color.BLACK);
        if(this.groups.size() > 1) {
            gc.strokeRect(left, top, right - left, bottom - top);
        }
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    @Override
    public void setZ(int newZ) {
        System.out.println("should not call setZ for Group");
    }

    @Override
    public int getZ() {
        System.out.println("should not call getZ for Group");
        return 0;
    }

    @Override
    public void setSize(int newSize) {
        System.out.println("should not set size for a group");
    }

    @Override
    public int getSize() {
        return groups.size();
    }

    public ArrayList<Groupable> getChildren() {
        return groups;
    }

    public void moveGroup(double dx, double dy) {
        for (Groupable g : groups) {
            g.moveGroup(dx, dy);
        }
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

}

