/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicstransformations;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author gutwin
 */
public class GraphicsTransformations extends Application {

    Canvas gCanvas;
    GraphicsContext gc;
    LineModel aLine;

    @Override
    public void start(Stage primaryStage) {
        gCanvas = new Canvas(1000, 800);
        gc = gCanvas.getGraphicsContext2D();
        aLine = new LineModel(-50, -50, 50, 50);

        gCanvas.setOnMouseMoved(this::handleMouseMoved);

        StackPane root = new StackPane();
        root.getChildren().add(gCanvas);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Graphics Transformations Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void handleMouseMoved(MouseEvent event) {
        aLine.scaleFactor = (event.getY() - gCanvas.getHeight() / 2) / (gCanvas.getHeight() / 2) * 5;
        aLine.rotation = event.getX() / gCanvas.getWidth() * (Math.PI * 2);
        aLine.transX = event.getX();
        aLine.transY = event.getY();

        aLine.calculateCoords();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, gCanvas.getWidth(), gCanvas.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(10);
        gc.strokeLine(0, gCanvas.getHeight() / 2, gCanvas.getWidth(), gCanvas.getHeight() / 2);

        gc.setStroke(Color.BLUEVIOLET);
        gc.strokeLine(aLine.showX1, aLine.showY1, aLine.showX2, aLine.showY2);
        gc.setFill(Color.ORANGE);
        gc.fillOval(aLine.showX1 - 10, aLine.showY1 - 10, 20, 20);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(24));
        gc.fillText("Translate: " + String.format("%.0f",aLine.transX) + "," + String.format("%.0f", aLine.transY), 10, 40);
        gc.fillText("Rotate: " + String.format("%.1f",aLine.rotation), 10, 80);
        gc.fillText("Scale: " + String.format("%.1f",aLine.scaleFactor), 10, 120);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    class LineModel {

        double x1, y1, x2, y2;
        double transX, transY;
        double scaleFactor;
        double rotation;

        double showX1;
        double showY1;
        double showX2;
        double showY2;

        public LineModel(double newX1, double newY1, double newX2, double newY2) {
            x1 = newX1;
            y1 = newY1;
            x2 = newX2;
            y2 = newY2;

            transX = 0;
            transY = 0;
            scaleFactor = 1;
            rotation = 0;
        }

        public void calculateCoords() {
            // rotation
            showX1 = x1 * Math.cos(-1 * rotation) - y1 * Math.sin(-1 * rotation);
            showY1 = x1 * Math.sin(-1 * rotation) + y1 * Math.cos(-1 * rotation);
            showX2 = x2 * Math.cos(-1 * rotation) - y2 * Math.sin(-1 * rotation);
            showY2 = x2 * Math.sin(-1 * rotation) + y2 * Math.cos(-1 * rotation);

            // scaling
            showX1 *= scaleFactor;
            showY1 *= scaleFactor;
            showX2 *= scaleFactor;
            showY2 *= scaleFactor;

            // translation
            showX1 += transX;
            showY1 += transY;
            showX2 += transX;
            showY2 += transY;
        }
    }
}
