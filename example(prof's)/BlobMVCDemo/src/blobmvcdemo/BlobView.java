/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobmvcdemo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author gutwin
 */
public class BlobView extends Pane implements BlobModelListener {

    Canvas blobCanvas;
    GraphicsContext gc;
    BlobViewController controller;
    BlobModel model;
    InteractionModel iModel;

    public BlobView(double width, double height) {
        blobCanvas = new Canvas(width, height);
        gc = blobCanvas.getGraphicsContext2D();
        getChildren().add(blobCanvas);
    }

    public void setController(BlobViewController aController) {
        controller = aController;
        blobCanvas.setOnMousePressed(controller::handleMousePressed);
        blobCanvas.setOnMouseDragged(controller::handleMouseDragged);
        blobCanvas.setOnMouseReleased(controller::handleMouseReleased);
    }

    public void setModel(BlobModel aModel) {
        model = aModel;
    }

    public void setInteractionModel(InteractionModel anIModel) {
        iModel = anIModel;
    }

    public void draw() {
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, blobCanvas.getWidth(), blobCanvas.getHeight());

        for (Blob b : model.blobs) {
            if (b == iModel.selected) {
                gc.setFill(Color.ORANGE);
            } else {
                gc.setFill(Color.LIGHTBLUE);
            }
            gc.fillOval(b.x - b.r, b.y - b.r, b.r * 2, b.r * 2);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(b.x - b.r, b.y - b.r, b.r * 2, b.r * 2);
        }
    }

    public void modelChanged() {
        draw();
    }

    public void modelChanged(double dx, double dy) {
        gc.save();
        if (iModel.selected != null) {
            Blob b = iModel.selected;
            double clipLeft, clipTop, clipWidth, clipHeight;
            clipLeft = Math.min(b.x - b.r, b.x - dx - b.r);
            clipWidth = 2 * b.r + Math.abs(dx);
            clipTop = Math.min(b.y - b.r, b.y - dy - b.r);
            clipHeight = 2 * b.r + Math.abs(dy);

            gc.beginPath();
            gc.rect(clipLeft-1, clipTop-1, clipWidth+2, clipHeight+2);
            gc.clip();
        }
        draw();
        gc.restore();
    }
}
