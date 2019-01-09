/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panningdemo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author gutwin
 */
public class PanningDemo extends Application {
    Canvas pictureCanvas;
    GraphicsContext gc;
    double viewportLeft, viewportTop;
    double prevX, prevY, dX, dY;
    double offsetX, offsetY;
    Image pic;
    


    @Override
    public void start(Stage primaryStage) {
        offsetX = 0;
        offsetY = 0;
        viewportLeft = 0;
        viewportTop = 0;
        
        pic = new Image("/octopus.jpg", true);
        pictureCanvas = new Canvas(600,600);
        gc = pictureCanvas.getGraphicsContext2D();
        pictureCanvas.setOnMousePressed(this::handleMousePressed);
        pictureCanvas.setOnMouseDragged(this::handleMouseDragged);
        pictureCanvas.setOnMouseReleased(this::handleMouseReleased);

        StackPane root = new StackPane();
        root.getChildren().add(pictureCanvas);
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        draw();
    }
    
    public void handleMousePressed(MouseEvent event) {
        prevX = event.getX();
        prevY = event.getY();
        draw();
    }

    public void handleMouseDragged(MouseEvent event) {
        dX = event.getX() - prevX;
        dY = event.getY() - prevY;
        prevX = event.getX();
        prevY = event.getY();

        viewportLeft -= dX / pic.getWidth();
        viewportTop -= dY / pic.getHeight();
        if (viewportLeft < 0) {
            viewportLeft = 0;
        }
        double viewportMaxLeft = (pic.getWidth() - pictureCanvas.getWidth()) / pic.getWidth();
        if (viewportLeft > viewportMaxLeft) {
            viewportLeft = viewportMaxLeft;
        }
        if (viewportTop < 0) {
            viewportTop = 0;
        }
        double viewportMaxTop = (pic.getHeight() - pictureCanvas.getHeight()) / pic.getHeight();
        if (viewportTop > viewportMaxTop) {
            viewportTop = viewportMaxTop;
        }
        offsetX = viewportLeft * pic.getWidth() * -1;
        offsetY = viewportTop * pic.getHeight() * -1;

        draw();
    }

    public void handleMouseReleased(MouseEvent event) {
        
    }
    
    public void draw() {
        gc.drawImage(pic, offsetX, offsetY);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(40));
        gc.fillText("viewportLeft: " + String.format("%.3f",viewportLeft), 10, 40);
        gc.fillText("viewportTop: " + String.format("%.3f",viewportTop), 10, 80);
        gc.fillText("offsetX: " + String.format("%.0f",offsetX), 10, 120);
        gc.fillText("offsetY: " + String.format("%.0f",offsetY), 10, 160);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
