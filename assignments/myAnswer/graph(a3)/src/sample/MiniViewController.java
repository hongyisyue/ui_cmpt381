package sample;

import javafx.scene.input.MouseEvent;

import java.util.Optional;

/**
 * Created by homeyxue on 2018-02-18.
 */
public class MiniViewController {

    GraphModel model;
    InteractionModel iModel;

    int state;
    final int STATE_READY = 0;
    final int STATE_DRAGGING = 1;

    double prevX, prevY, dX, dY;

    public MiniViewController(){
    }
    public void setModel(GraphModel mModel) {
        model = mModel;
    }

    public void setInteractionModel(InteractionModel IModel){iModel = IModel;}

    public boolean contains(double eventX, double eventY){
        double viewLeft = model.portX / 1000.0*100.0;
        double viewTop = model.portY / 1000.0*100.0;
        double viewRight = 50.0;
        double viewBottom = 50.0;

        if(viewLeft+viewRight >= eventX  && eventX >= viewLeft && eventY >= viewTop && eventY <= viewTop+viewBottom){
            return true;
        } else {
            return false;
        }
    }

    public void handleMousePressed(MouseEvent event){
        switch (state){
            case STATE_READY:
                if(contains(event.getX(), event.getY())){
                    prevX = event.getX();
                    prevY = event.getY();
                    state = STATE_DRAGGING;
                }
                break;
        }
    }

    public void hanleMouseDragging(MouseEvent event){
        switch (state){
            case STATE_DRAGGING:
                dX = (event.getX() - prevX)*10;
                dY = (event.getY() - prevY)*10;
                model.moveViewport(-dX, -dY);
                prevX = event.getX();
                prevY = event.getY();
                state = STATE_DRAGGING;
                break;
        }
    }

    public void handleMouseReleased(MouseEvent event){
        switch (state){
            case STATE_DRAGGING:
                state = STATE_READY;
                break;
        }
    }
}
