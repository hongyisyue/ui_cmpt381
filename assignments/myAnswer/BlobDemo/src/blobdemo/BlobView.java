/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BlobView extends Pane implements ModelListener {

    Canvas blobCanvas;
    GraphicsContext gc;
    BlobModel model;
    InteractionModel iModel;

    public BlobView(double width, double height) {
        blobCanvas = new Canvas(width, height);
        gc = blobCanvas.getGraphicsContext2D();
        getChildren().add(blobCanvas);
    }

    public void setModel(BlobModel aModel) {
        model = aModel;
    }

    public void setInteractionModel(InteractionModel anIModel) {
        iModel = anIModel;
    }

    public void draw() {
        // draw background
        if (iModel.controlDown) {
            gc.setFill(Color.GREY);
        } else {
            gc.setFill(Color.DARKGREY);
        }
        gc.fillRect(0, 0, blobCanvas.getWidth(), blobCanvas.getHeight());

        // draw rubberband if exists
        if (iModel.hasRubberband()) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(iModel.rubber.left, iModel.rubber.top, iModel.rubber.width, iModel.rubber.height);
        }

        // draw blobs
        //model.getBlobs().forEach(b -> drawBlob(b));
        for (Groupable g: model.blobs) {

            if (g.getSize() == 1){
                drawBlob(g);
            }

            if (g.getSize() > 1) {
                if (iModel.isSelected(g)) {
                    gc.setFill(Color.ORANGE);
                } else {
                    gc.setFill(Color.LIGHTBLUE);
                }
                g.drawGroup(gc);
            }
        }
    }

    private void drawBlob(Groupable b) {
        if (iModel.isSelected(b)) {
            gc.setFill(Color.ORANGE);
        } else {
            gc.setFill(Color.LIGHTBLUE);
        }
        gc.fillOval(b.getLeft(), b.getTop(), b.getRight()-b.getLeft(), b.getBottom()-b.getTop());
        gc.setStroke(Color.BLACK);
        gc.strokeOval(b.getLeft(), b.getTop(), b.getRight()-b.getLeft(), b.getBottom()-b.getTop());
    }

    public void modelChanged() {
        draw();
    }
}

