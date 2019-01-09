/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetracker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author gutwin
 */
public class MouseTracker extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        TrackerView view = new TrackerView();
        TrackerTextView tView = new TrackerTextView();
        TrackerModel model = new TrackerModel();
        TrackerViewController controller = new TrackerViewController();
        
        view.setModel(model);
        tView.setModel(model);
        controller.setModel(model);
        model.addSubscriber(view);
        model.addSubscriber(tView);
        
        view.setOnMouseMoved(controller::handleMouseMoved);
        view.setOnMouseClicked(controller::handleMouseClicked);
        
        VBox root = new VBox();
        root.getChildren().addAll(view, tView);
        Scene scene = new Scene(root);
        
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
