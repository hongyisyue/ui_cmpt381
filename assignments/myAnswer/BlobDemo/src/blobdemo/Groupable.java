/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

/**
 * Created by homeyxue on 2018-03-28.
 */
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public interface Groupable {

    boolean hasChildren();
    ArrayList<Groupable> getChildren();
    boolean contains(double x, double y);
    boolean isContained(double x1, double y1, double x2, double y2);

    void drawGroup(GraphicsContext gc);
    void moveGroup(double dx, double dy);

    double getLeft(); // bounding box of the group
    double getRight();
    double getTop();
    double getBottom();
    void setZ(int newZ); // Z-order for the group
    int getZ();
    void setSize(int newSize); //size of the groupable
    int getSize();

}
