/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

/**
 * Created by homeyxue on 2018-03-23.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BlobDemo extends Application {

    BlobView view;
    BlobViewController controller;
    BlobModel model;
    InteractionModel iModel;

    ObservableList<String> undoItems;
    ObservableList<String> redoItems;
    Label clipLabel;

    @Override
    public void start(Stage primaryStage) {

        view = new BlobView(1000, 600);
        controller = new BlobViewController();
        model = new BlobModel();
        iModel = new InteractionModel();

        controller.setModel(model);
        controller.setInteractionModel(iModel);
        controller.setMain(this);
        controller.setView(view);

        view.setModel(model);
        view.setInteractionModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        view.setOnMousePressed(controller::handleMousePressed);
        view.setOnMouseDragged(controller::handleMouseDragged);
        view.setOnMouseReleased(controller::handleMouseReleased);
        //view.setOnMouseClicked(controller::handleMouseClicked);
        view.setOnKeyPressed(controller::handleKeyPressed);
        view.setOnKeyReleased(controller::handleKeyReleased);

        Label undoLabel = new Label("Undo Stack");
        Label redoLabel = new Label("Redo Stack");

        clipLabel = new Label("Clipboard: " + controller.clipboard.result());

        ListView<String> undoList = new ListView<>();
        undoList.setPrefHeight(800);
        ListView<String> redoList = new ListView<>();
        redoList.setPrefHeight(800);
        undoItems =FXCollections.observableArrayList ("Empty");
        redoItems =FXCollections.observableArrayList ("Empty");
        undoList.setItems(undoItems);
        redoList.setItems(redoItems);
        Button undoButton = new Button("Undo!");
        Button redoButton = new Button("Redo!");

        undoButton.setOnAction(controller::handleUndo);
        redoButton.setOnAction(controller::handleRedo);

        VBox undoVB = new VBox();
        VBox redoVB = new VBox();
        VBox viewVB = new VBox();
        undoVB.setAlignment(Pos.CENTER);
        redoVB.setAlignment(Pos.CENTER);
        viewVB.setAlignment(Pos.CENTER);
        undoVB.getChildren().addAll(undoLabel,undoList,undoButton);
        redoVB.getChildren().addAll(redoLabel,redoList,redoButton);
        viewVB.getChildren().addAll(view, clipLabel);

        HBox root = new HBox();
        root.getChildren().addAll(undoVB, viewVB, redoVB);

        Scene scene = new Scene(root, 1500, 700);

        primaryStage.setScene(scene);
        primaryStage.show();

        view.draw();
        view.requestFocus();
    }

    public void updateUndoList(Stack<BlobCommand> undoStack) {
        undoItems.clear();
        for (int i = undoStack.size() - 1; i >= 0; i--) {
            BlobCommand bc = undoStack.get(i);
            undoItems.add(bc.toString());
        }
    }

    public void updateRedoList(Stack<BlobCommand> redoStack) {
        redoItems.clear();
        for (int i = redoStack.size() - 1; i >= 0; i--) {
            BlobCommand bc = redoStack.get(i);
            redoItems.add(bc.toString());
        }
    }

    public void updateClipLabel(BlobViewController myController){
        clipLabel.setText("Clipboard: " + myController.clipboard.result());
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
