/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetracker;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author gutwin
 */
public class TrackerViewController {

    TrackerModel model;
    double prevX, prevY;

    public TrackerViewController() {
        prevX = Double.MAX_VALUE;
        prevY = Double.MAX_VALUE;
    }

    public void setModel(TrackerModel aModel) {
        model = aModel;
    }

    public void handleMouseMoved(MouseEvent event) {
        if (prevX != Double.MAX_VALUE) {
            double distance = dist(prevX, prevY, event.getX(), event.getY());
            model.addMovement(distance);
        }
        prevX = event.getX();
        prevY = event.getY();
    }

    public void handleMouseClicked(MouseEvent event) {
        model.addClick();
    }

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
