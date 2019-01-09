package sample;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.util.converter.NumberStringConverter;

import java.text.DecimalFormat;

public class Main extends Application implements EventHandler<ActionEvent>{

    Slider slider1;
    Button button1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("381 Custom Widget");



        BarControl bar1 = new BarControl();
        BarControl bar2 = new BarControl();
        BarControl bar3 = new BarControl();

        Label bar1Value =new Label(Double.toString(bar1.getAmountDue()));   //first bar's value
        Label bar2Value = new Label(Double.toString(bar2.getAmountDue()));  //second bar's value
        Label bar3Value = new Label(Double.toString(bar3.getAmountDue()));  //third bar's value


        /*
        value of bar2 and bar2 us listener
         */
        bar1.amountDueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Double temp = bar1.getAmountDue();
                bar1Value.setText(String.format("%.1f", temp));
            }
        });

        bar2.amountDueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Double temp = bar2.getAmountDue();
                bar2Value.setText(String.format("%.1f", temp));
            }
        });

        /*
        value of bar3 us binding
         */
        bar3Value.textProperty().bind(bar3.amountDueProperty().asString());

        slider1 = new Slider(150.0, 1000.0, 275.0);

        Label sliderValue = new Label(Double.toString(slider1.getValue()));

        button1 = new Button();
        button1.setText("Quit");
        button1.setOnAction(this);

        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sliderValue.setText(String.format("%.2f", newValue));
            }
        });
        HBox hbox = new HBox(8);

        hbox.getChildren().addAll(bar1,bar1Value,  bar2, bar2Value, bar3, bar3Value);
        VBox vbox = new VBox(8); // spacing = 8
        vbox.getChildren().addAll( hbox, slider1, sliderValue, button1);

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
            System.out.println("Goodbye");
            System.exit(0);
        }
    }
}
