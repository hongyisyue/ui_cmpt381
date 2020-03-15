package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    MainGraphView mView;
    Controller mController;
    GraphModel mModel;
    InteractionModel iModel;
    MiniGraphView miniView;
    MiniViewController miniController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        mView = new MainGraphView();
        mController = new Controller();
        mModel = new GraphModel();
        iModel = new InteractionModel();
        miniController = new MiniViewController();


        miniView = new MiniGraphView();

        mController.setModel(mModel);
        mController.setInteractionModel(iModel);
        mView.setModel(mModel);
        mView.setInteractionModel(iModel);
        mView.setController(mController);
        mView.setMiniView(miniView);

        miniController.setInteractionModel(iModel);
        miniController.setModel(mModel);
        miniView.setModel(mModel);
        miniView.setInteractionModel(iModel);
        miniView.setWatchingView(mView);
        miniView.setController(miniController);


        mModel.addSubscriber(mView);
        mModel.addSubscriber(miniView);
        iModel.addSubscriber(mView);

        Pane root = new Pane();
        root.getChildren().addAll(miniView);
        root.getChildren().addAll(mView);
        miniView.toFront();

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Graph Demo!");
        primaryStage.show();

        mView.draw();
        miniView.draw();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
