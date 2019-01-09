/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobmvcdemo;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author gutwin
 */
public class BlobMVCDemo extends Application {

    BlobView view;
    BlobViewController controller;
    BlobModel model;
    InteractionModel iModel;
    
    @Override
    public void start(Stage primaryStage) {
        view = new BlobView(1000, 500);
        controller = new BlobViewController();
        model = new BlobModel();
        iModel = new InteractionModel();
        
        controller.setModel(model);
        controller.setInteractionModel(iModel);
        view.setModel(model);
        view.setInteractionModel(iModel);
        view.setController(controller);
        model.addSubscriber(view);
        iModel.addSubscriber(view);
        
        Scene scene = new Scene(view);

        primaryStage.setScene(scene);
        primaryStage.show();
        
        view.draw();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
