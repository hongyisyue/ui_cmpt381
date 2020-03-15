/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobmvcdemo;

import java.util.Optional;
import java.util.Set;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author gutwin
 */
public class BlobViewController {

    BlobModel model;
    InteractionModel iModel;

    int state;
    final int STATE_READY = 0;
    final int STATE_DRAGGING = 1;

    double prevX, prevY, dX, dY;

    public BlobViewController() {

    }

    public void setModel(BlobModel aModel) {
        model = aModel;
    }

    public void setInteractionModel(InteractionModel anIModel) {
        iModel = anIModel;
    }

    public void handleMousePressed(MouseEvent event) {
        switch (state) {
            case STATE_READY:
                // on blob or background?
                Optional<Blob> maybeBlob = model.find(event.getX(), event.getY());
                if (maybeBlob.isPresent()) {
                    iModel.setSelected(maybeBlob.get());
                    model.raiseBlob(iModel.selected);
                    prevX = event.getX();
                    prevY = event.getY();
                    state = STATE_DRAGGING;
                } else {
                    model.addBlob(event.getX(), event.getY());
                    state = STATE_READY;
                }
                break;
        }
    }

    public void handleMouseDragged(MouseEvent event) {
        switch (state) {
            case STATE_DRAGGING:
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                model.moveBlob(iModel.selected, dX, dY);
                prevX = event.getX();
                prevY = event.getY();
                state = STATE_DRAGGING;
                break;
        }
    }

    public void handleMouseReleased(MouseEvent event) {
        switch (state) {
            case STATE_DRAGGING:
                iModel.setSelected(null);
                state = STATE_READY;
                break;
        }
    }

    //public void handleClick(MouseEvent event) {
    //    Optional<Blob> maybeBlob = model.find(event.getX(), event.getY());
    //    if (maybeBlob.isPresent()) {
    //        iModel.setSelected(maybeBlob.get());
    //    } else {
    //        model.addBlob(event.getX(), event.getY());
    //    }

        //if (model.contains(event.getX(), event.getY()))  {
        //    Blob target = model.find(event.getX(), event.getY());
        //    iModel.setSelected(target);
        //} else {
        //    model.addBlob(event.getX(), event.getY());
        //}
    //}
}
