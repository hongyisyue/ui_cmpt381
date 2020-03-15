/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetracker;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author gutwin
 */
public class TrackerView extends Pane implements TrackerModelListener {

    Canvas trackerCanvas;
    GraphicsContext gc;
    TrackerModel model;

    public TrackerView() {
        trackerCanvas = new Canvas(500,500);
        gc = trackerCanvas.getGraphicsContext2D();
        getChildren().add(trackerCanvas);
    }

    public void setModel(TrackerModel aModel) {
        model = aModel;
    }

    public void draw() {
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, 500, 500);

        // bar for clicks
        gc.setFill(Color.YELLOW);
        double barHeight = model.numberOfClicks * 5;
        gc.fillRect(50, 500-barHeight, 150, barHeight);

        // bar for movement
        gc.setFill(Color.GREEN);
        barHeight = model.totalMovement / 100;
        //System.out.println(barHeight);
        gc.fillRect(250, 500-barHeight, 150, barHeight);
    }
    
    public void modelChanged() {
        draw();
    }
}
