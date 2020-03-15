/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hox294_as0;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 *
 * @author homeyxue
 */
public class Hox294_as0 extends Application implements EventHandler<ActionEvent>{
  Button button1;
    Label label1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");

        label1 = new Label("Hello World");

        button1 = new Button();
        button1.setText("say 'Hello World' ");
        button1.setOnAction(this);

        VBox vbox = new VBox(8); // spacing = 8
        vbox.getChildren().addAll(button1, label1);

        primaryStage.setScene(new Scene(vbox, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == button1){
            label1.setText("Hello 381!");
        }
    }
}
