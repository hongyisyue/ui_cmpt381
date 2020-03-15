package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Created by homeyxue on 2018-02-06.
 */
public class MiniGraphView extends Pane implements listener{
    Canvas GraphCanvas;
    GraphicsContext gc;
    GraphModel model;
    MainGraphView mainView;
    MiniViewController myController;
    InteractionModel iModel;

    public MiniGraphView(){
        GraphCanvas = new Canvas(100,100);
        gc = GraphCanvas.getGraphicsContext2D();
        getChildren().add(GraphCanvas);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, 100, 100);
    }

    public void setModel(GraphModel mModel){model = mModel;}

    public void setWatchingView(MainGraphView mView){
        mainView = mView;
    }

    public void setInteractionModel(InteractionModel mIModel){
        iModel = mIModel;
    }

    public void setController(MiniViewController aController){
        myController = aController;
            GraphCanvas.setOnMousePressed(myController::handleMousePressed);
            GraphCanvas.setOnMouseDragged(myController::hanleMouseDragging);
            GraphCanvas.setOnMouseReleased(myController::handleMouseReleased);
    }

    public void draw(){
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, 100, 100);

        double viewLeft = mainView.model.portX / mainView.getWidth() * this.getWidth();
        double viewTop = mainView.model.portY / mainView.getHeight() * this.getHeight();
        double viewRight = 500 / mainView.getWidth() * this.getWidth();
        double viewBottom = 500 / mainView.getWidth() * this.getHeight();
        gc.setFill(Color.GREEN);
        gc.fillRect(viewLeft, viewTop, viewRight, viewBottom);

        for(Vertex v: model.vertexList){
            drawVertex(v);
        }

        for(Edge e: model.edgeList){
            drawEdge(e);
        }

    }

    public void drawVertex(Vertex v) {
        /**
         * draw circle
         * x is the x of upper left bound
         * y is the y of upper left bound
         * width is the width of center
         * height is the height of center
         */
        double x = (v.x - v.radius) / mainView.getWidth() * getWidth();
        double y = (v.y - v.radius) / mainView.getHeight() *getHeight();
        double width = (v.radius*2) / mainView.getWidth() * getWidth();
        double height = (v.radius*2) / mainView.getHeight() *getHeight();

            gc.setFill(Color.LIGHTBLUE);

        gc.fillOval(x, y, width, height);
    }

    public void drawEdge(Edge e){
        /**
         * draw edge
         * (x0, y0) is the starting point of the line
         * (x1, y1) is the ending point of the line
         */
        double x0 = (e.start.x)/ mainView.getWidth() * getWidth();
        double y0 = (e.start.y)/ mainView.getHeight() *getHeight();
        double x1 = (e.end.x)/ mainView.getWidth() * getWidth();
        double y1 = (e.end.y)/ mainView.getHeight() *getHeight();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);
        gc.strokeLine(x0, y0, x1, y1);
    }

    @Override
    public void modelChanged() {
        draw();
    }

    @Override
    public void modelChanged(double dx, double dy){
    }
}
