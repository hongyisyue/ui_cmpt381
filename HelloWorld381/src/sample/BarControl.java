package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by homeyxue on 2018-01-16.
 */
public class BarControl extends Pane{
    GraphicsContext gc;


    // Define the property
    private DoubleProperty amountDue = new SimpleDoubleProperty();

    // Define a getter for the property's value
    public final double getAmountDue(){return amountDue.get();}

    // Define a setter for the property's value
    public final void setAmountDue(double value){amountDue.set(value);}

    // Define a getter for the property itself
    public DoubleProperty amountDueProperty() {return amountDue;}

    String temp3;

    Double value;    //the value of bar

    public BarControl() {
        Canvas widgetCanvas = new Canvas(50, 200);
        gc = widgetCanvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,50,200);    //black background

        value = 250.0;  //initial value of 250.0
        setAmountDue(value);

        gc.setFill(Color.YELLOW);
        gc.fillRect(10,125,30,70);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(widgetCanvas);
        this.getChildren().add(hbox);

        widgetCanvas.setOnMouseDragged(e ->{

            gc.setFill(Color.BLACK);
            gc.fillRect(0,0,50,200);    //refill black background every time

            if(e.getY() >= 200 ){
                gc.setFill(Color.YELLOW);
                gc.fillRect(10,194,30,1);

                value = 100.0;
                setAmountDue(value);//.setText(Double.toString(100.0));  //lower limit
            }

            else if(e.getY() <= 10 ){
                gc.setFill(Color.YELLOW);
                gc.fillRect(10,5,30,190);

                value = 500.0;
                setAmountDue(value);//.setText(Double.toString(500.0));  //upper limit
            }

            else {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, 50, 200);

                gc.setFill(Color.YELLOW);
                gc.fillRect(10, e.getY() - 5, 30, 200 - e.getY());

                Number temp = 523.273 - 2.127*e.getY();
                value = temp.doubleValue();

                setAmountDue(Double.parseDouble(String.format("%.1f", value)));//value changes when cursor moves

            }
        });
    }


}
