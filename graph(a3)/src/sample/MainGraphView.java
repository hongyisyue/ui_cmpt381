package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Created by homeyxue on 2018-02-06.
 */
public class MainGraphView extends Pane implements listener{
    Canvas GraphCanvas;
    GraphicsContext gc;
    GraphModel model;
    Controller myController;
    InteractionModel iModel;
    MiniGraphView mini;

    public MainGraphView(){
        GraphCanvas = new Canvas(1000, 1000);
        gc = GraphCanvas.getGraphicsContext2D();
        getChildren().add(GraphCanvas);

        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, 1000, 1000);
    }

    public void setModel(GraphModel gModel) {
        model = gModel;
    }

    public void setMiniView(MiniGraphView aView) {
        mini = aView;
    }
    public void setController(Controller aController){
        myController = aController;
        GraphCanvas.setOnMousePressed(myController::handleMousePressed);
        GraphCanvas.setOnMouseDragged(myController::handleMouseDragged);
        GraphCanvas.setOnMouseReleased(myController::handleMouseReleased);
    }

    public void setInteractionModel(InteractionModel IModel){
        iModel = IModel;
    }

    public void draw(){
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, 1000, 1000);

        drawActiveEdge();

        for(Edge e: model.edgeList){
            drawEdge(e);
        }
        for(Vertex v: model.vertexList){
            drawVertex(v);
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
        double x = (v.x - v.radius) - model.portX;
        double y = (v.y - v.radius) - model.portY;
        double width = (v.radius*2) ;
        double heigh = (v.radius*2) ;

        if(v == iModel.selected) {
            // draw selected vertex in orange
            gc.setFill(Color.ORANGE);

        }
            // draw black border if shift-selected
        else if(v == iModel.shiftSelected) {
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(3);
                gc.strokeOval(x, y, width, heigh);
            }

        else{
            gc.setFill(Color.LIGHTBLUE);
        }

        gc.fillOval(x, y, width, heigh);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeOval(x, y, width, heigh);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText(""+v.id, x+v.radius, y+v.radius);
    }

    public void drawEdge(Edge e){
        /**
         * draw edge
         * (x0, y0) is the starting point of the line
         * (x1, y1) is the ending point of the line
         */
        double x0 = e.start.x - model.portX;
        double y0 = e.start.y - model.portY;
        double x1 = e.end.x - model.portX;
        double y1 = e.end.y - model.portY;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeLine(x0, y0, x1, y1);
    }

    public void drawActiveEdge(){
        if(iModel.shiftSelected != null && iModel.tempEdgeX != 0.0 && iModel.tempEdgeY != 0.0){
            gc.setStroke(Color.BLACK);
            double x0 = iModel.shiftSelected.x - model.portX;
            double y0 = iModel.shiftSelected.y - model.portY;
            gc.setLineWidth(3);
            gc.strokeLine(x0, y0, iModel.tempEdgeX, iModel.tempEdgeY);
        }
    }

    @Override
    public void modelChanged() {
        draw();
    }

    @Override
    public void modelChanged(double dx, double dy){
        gc.save();
        if(iModel.selected != null){
            Vertex v = iModel.selected;
            double clipLeft, clipTop, clipWidth, clipHeight;
            clipLeft = Math.min(v.x - v.radius, v.x - dx - v.radius);
            clipWidth = 2 * v.radius + Math.abs(dx);
            clipTop = Math.min(v.y - v.radius, v.y - dy - v.radius);
            clipHeight = 2 * v.radius + Math.abs(dy);

            gc.beginPath();
            gc.rect(clipLeft-1, clipTop-1, clipWidth+2, clipHeight+2);
            gc.clip();
        }
        draw();
        gc.restore();
        }
    }
