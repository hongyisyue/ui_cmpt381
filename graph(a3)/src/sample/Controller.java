package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class Controller{

    GraphModel model;
    InteractionModel iModel;

    int state;
    final int STATE_READY = 0;
    final int STATE_DRAGGING = 1;
    final int STATE_DRAWING_EDGE = 2;
    final int STATE_BACKGROUND = 3;

    double prevX, prevY, dX, dY;

    public Controller(){

    }

    public void setModel(GraphModel mModel) {
        model = mModel;
    }

    public void setInteractionModel(InteractionModel IModel){iModel = IModel;}

    public void handleMousePressed(MouseEvent event){
        switch (state){
            case STATE_READY:
                Optional<Vertex> maybeVertex = model.findClickedVertex(event.getX(), event.getY());
                if(maybeVertex.isPresent()){
                    if(event.isShiftDown()){
                        iModel.setShiftSelected(maybeVertex.get());
                        prevX = event.getX();
                        prevY = event.getY();
                        iModel.setTempEdgeEnd(event.getX(), event.getY());
                        state = STATE_DRAWING_EDGE;
                    } else {
                        iModel.setSelected(maybeVertex.get());
                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_DRAGGING;
                    }
                } else {
                    prevX = event.getX();
                    prevY = event.getY();
                    state = STATE_READY;
                }
                break;
        }
    }

    public void handleMouseDragged(MouseEvent event){
        switch (state) {
            case STATE_READY:
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                model.moveViewport(dX, dY);
                prevX = event.getX();
                prevY = event.getY();
                state = STATE_BACKGROUND;
                break;

            case STATE_BACKGROUND:
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                model.moveViewport(dX, dY);
                prevX = event.getX();
                prevY = event.getY();
                state = STATE_BACKGROUND;
                break;

            case STATE_DRAGGING:
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                model.moveVertex(iModel.selected, dX, dY);
                prevX = event.getX();
                prevY = event.getY();
                state = STATE_DRAGGING;
                break;

            case STATE_DRAWING_EDGE:
                iModel.setTempEdgeEnd(event.getX(), event.getY());
                prevX = event.getX();
                prevY = event.getY();
                state = STATE_DRAWING_EDGE;
                break;

        }
    }

    public void handleMouseReleased(MouseEvent event){
        switch (state) {
            case STATE_READY:
                model.addVertex(event.getX(), event.getY());
                state = STATE_READY;
                break;

            case STATE_BACKGROUND:
                state = STATE_READY;
                break;

            case STATE_DRAGGING:
                iModel.setSelected(null);
                state = STATE_READY;
                break;

            case STATE_DRAWING_EDGE:
                    Optional<Vertex> maybeVertex = model.findClickedVertex(event.getX(), event.getY());
                    if(maybeVertex.isPresent() && (iModel.shiftSelected != maybeVertex.get())){
                        model.addEdge(iModel.shiftSelected, maybeVertex.get());
                    }

                iModel.tempEdgeX = 0.0;
                iModel.tempEdgeY = 0.0;
                iModel.setShiftSelected(null);
                iModel.setSelected(null);
                state = STATE_READY;
                break;
        }
    }
}
