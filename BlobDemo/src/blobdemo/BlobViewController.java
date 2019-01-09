/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

/**
 * Created by homeyxue on 2018-03-23.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import javafx.event.ActionEvent;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class BlobViewController {
    BlobModel model;
    InteractionModel iModel;

    Stack<BlobCommand> undoStack, redoStack;
    BlobClipboard clipboard;
    BlobDemo mMain;

    BlobView mView;

    private enum UIState {
        STATE_READY,
        STATE_CONTROL_READY,
        STATE_DRAGGING,
        STATE_CONTROL_DRAGGING,
        STATE_BACKGROUND,
        STATE_CONTROL_BACKGROUND,
        STATE_RUBBERBAND,
        STATE_CONTROL_RUBBERBAND
    }
    UIState state;

    double prevX, prevY, dX, dY;
    double dragStartX, dragStartY;

    public BlobViewController() {

        state = UIState.STATE_READY;
        clipboard = new BlobClipboard();
        undoStack = new Stack<>();
        redoStack = new Stack<>();

    }

    public void setMain(BlobDemo aMain){mMain = aMain;}

    public void setModel(BlobModel aModel) {
        model = aModel;
    }

    public void setInteractionModel(InteractionModel anIModel) {
        iModel = anIModel;
    }

    public void setView(BlobView aView){mView = aView;}

    public void handleMousePressed(MouseEvent event) {
        Optional<Groupable> maybeBlob = model.find(event.getX(), event.getY());
        switch (state) {
            case STATE_READY:
                // on blob or background?
                if (maybeBlob.isPresent()) {
                    // hit blob
                    Groupable b = maybeBlob.get();
                    model.raiseBlob(b);

                    // selected or not?
                    if (iModel.isSelected(b)) {
                        // selected, so just start dragging
                        prevX = event.getX();
                        prevY = event.getY();
                        dragStartX = event.getX();
                        dragStartY = event.getY();
                        state = UIState.STATE_DRAGGING;
                    } else {
                        // not selected, so select (only) this blob
                        // and then start dragging
                        iModel.clearSelection();
                        iModel.addSubtractSelection(b);
                        prevX = event.getX();
                        prevY = event.getY();
                        dragStartX = event.getX();
                        dragStartY = event.getY();
                        state = UIState.STATE_DRAGGING;
                    }
                } else {
                    // no hit (click is on the background)
                    // clear the selection, and prepare for a rubberband action
                    iModel.clearSelection();
                    state = UIState.STATE_BACKGROUND;
                }
                break;
            case STATE_CONTROL_READY:
                // on blob?
                if (maybeBlob.isPresent()) {
                    // hit blob
                    Groupable b = maybeBlob.get();
                    model.raiseBlob(b);

                    // add/subtract each clicked blob
                    iModel.addSubtractSelection(b);

                    // if we are now on a selected blob,
                    // start dragging
                    if (iModel.isSelected(b)) {
                        // selected, so start dragging
                        prevX = event.getX();
                        prevY = event.getY();
                        dragStartX = event.getX();
                        dragStartY = event.getY();
                        state = UIState.STATE_CONTROL_DRAGGING;
                    }
                } else {
                    // no hit (click is on the background)
                    // prepare for a rubberband action
                    state = UIState.STATE_CONTROL_BACKGROUND;
                }
                break;
        }
    }

    public void handleMouseDragged(MouseEvent event) {
        switch (state) {
            case STATE_DRAGGING:
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                model.moveBlobs(iModel.selectionSet, dX, dY);
                prevX = event.getX();
                prevY = event.getY();
                break;
            case STATE_CONTROL_DRAGGING:
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                model.moveBlobs(iModel.selectionSet, dX, dY);
                prevX = event.getX();
                prevY = event.getY();
                break;
            case STATE_BACKGROUND:
                // we are starting a rubberband selection
                state = UIState.STATE_RUBBERBAND;
                iModel.setRubberbandStart(event.getX(), event.getY());
                break;
            case STATE_RUBBERBAND:
                // we are continuing a rubberband selection
                iModel.setRubberbandEnd(event.getX(), event.getY());
                break;
            case STATE_CONTROL_BACKGROUND:
                // we are starting a rubberband selection
                state = UIState.STATE_CONTROL_RUBBERBAND;
                iModel.setRubberbandStart(event.getX(), event.getY());
                break;
            case STATE_CONTROL_RUBBERBAND:
                // we are continuing a rubberband selection
                iModel.setRubberbandEnd(event.getX(), event.getY());
                break;
        }
    }

    public void handleMouseReleased(MouseEvent event) {
        List<Groupable> rubberSet;
        switch (state) {
            case STATE_DRAGGING:
                dX = event.getX() - dragStartX;
                dY = event.getY() - dragStartY;
                MoveCommand mc = new MoveCommand(model, iModel.selectionSet, dX, dY);
                undoStack.push(mc);
                mMain.updateUndoList(undoStack);
                state = UIState.STATE_READY;
                break;
            case STATE_CONTROL_DRAGGING:
                dX = event.getX() - dragStartX;
                dY = event.getY() - dragStartY;
                MoveCommand mc2 = new MoveCommand(model, iModel.selectionSet, dX, dY);
                undoStack.push(mc2);
                mMain.updateUndoList(undoStack);
                state = UIState.STATE_CONTROL_READY;
                break;
            case STATE_BACKGROUND:
                // we are just clicking on the background
                // so clear the selection
                iModel.clearSelection();
                state = UIState.STATE_READY;
                break;
            case STATE_RUBBERBAND:
                // we are finishing a rubberband selection
                // so check whether we selected anything
                rubberSet = model.findRubber(iModel.rubber.left, iModel.rubber.top,
                        iModel.rubber.left + iModel.rubber.width, iModel.rubber.top + iModel.rubber.height);
                iModel.addSubtractSelection(rubberSet);
                iModel.deleteRubber();
                state = UIState.STATE_READY;
                break;
            case STATE_CONTROL_RUBBERBAND:
                // we are finishing a rubberband selection
                // so check whether we selected anything
                rubberSet = model.findRubber(iModel.rubber.left, iModel.rubber.top,
                        iModel.rubber.left + iModel.rubber.width, iModel.rubber.top + iModel.rubber.height);
                iModel.addSubtractSelection(rubberSet);
                iModel.deleteRubber();
                state = UIState.STATE_CONTROL_READY;
                break;
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            //System.out.println("Control down");
            switch (state) {
                case STATE_READY:
                    state = UIState.STATE_CONTROL_READY;
                    iModel.setControl(true);
                    break;
                case STATE_DRAGGING:
                    state = UIState.STATE_CONTROL_DRAGGING;
                    iModel.setControl(true);
                    break;
                case STATE_RUBBERBAND:
                    state = UIState.STATE_CONTROL_RUBBERBAND;
                    iModel.setControl(true);
                    break;
            }
        } else if (event.getCode() == KeyCode.A) {
             System.out.println("A");
            mMain.updateUndoList(undoStack);
            Groupable aBlob = new Blob(Math.random() * 1000, Math.random() * 600, 15, 0);
            CreateCommand cc = new CreateCommand(model, aBlob, (aBlob.getLeft()+aBlob.getRight())/2, (aBlob.getBottom()+aBlob.getTop())/2);
            //cc.doit();
            undoStack.push(cc);
            mMain.updateUndoList(undoStack);
            model.addBlob(aBlob);
            //model.addBlob(Math.random() * 1000, Math.random() * 600);
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            //System.out.println("Control up");

            switch (state) {
                case STATE_CONTROL_READY:
                    state = UIState.STATE_READY;
                    iModel.setControl(false);
                    break;
                case STATE_CONTROL_DRAGGING:
                    state = UIState.STATE_DRAGGING;
                    iModel.setControl(false);
                    break;
                case STATE_CONTROL_RUBBERBAND:
                    state = UIState.STATE_RUBBERBAND;
                    iModel.setControl(false);
                    break;
            }
        }
        else if(event.getCode() == KeyCode.G) {
            if(iModel.selectionSet.size() >1){
                Groupable tempGroup = model.createGroup(iModel.selectionSet);
                iModel.selectionSet.clear();
                iModel.selectionSet.add(tempGroup);
                model.modelChanged();
                mMain.updateClipLabel(this);
            }
        }
        else if(event.getCode() == KeyCode.U) {
            if(iModel.selectionSet.size() == 1 && iModel.selectionSet.get(0).hasChildren()){
                ArrayList<Groupable> items = model.ungroup(iModel.selectionSet.get(0));
                iModel.selectionSet.clear();
                addRemoveSelection(items);
                model.modelChanged();
                mMain.updateClipLabel(this);
            }
        }
        else if(event.getCode() == KeyCode.C && event.isControlDown()){
            if(iModel.selectionSet.size() > 0){
                clipboard.copy(iModel.selectionSet);
                mMain.updateClipLabel(this);
            }
        }
        else if(event.getCode() == KeyCode.V && event.isControlDown()){
            if(clipboard.group.size() > 0) {

                iModel.selectionSet.clear();
                iModel.selectionSet = clipboard.paste();
                model.addGroup(iModel.selectionSet);

//                iModel.selectionSet.clear();
//                iModel.selectionSet = clipboard.paste();
            }
        }
        else if(event.getCode() == KeyCode.X && event.isControlDown()){
            if(iModel.selectionSet.size() > 0){
                clipboard.copy(iModel.selectionSet);
                model.deleteGroups(iModel.selectionSet);
                mMain.updateClipLabel(this);
            }
        }
    }

    private void addRemoveSelection(Groupable sGroup) {
        if (iModel.selectionSet.contains(sGroup)) {
            iModel.selectionSet.remove(sGroup);
        } else {
            iModel.selectionSet.add(sGroup);
        }
    }

    private void addRemoveSelection(ArrayList<Groupable> sGroups) {
        for (Groupable g : sGroups) {
            addRemoveSelection(g);
        }
    }

    public void handleUndo(ActionEvent event) {
        if (undoStack.empty()) {
            System.out.println("Nothing more to undo!");
        } else {
            BlobCommand bc = undoStack.pop();
            bc.undo();
            mMain.updateUndoList(undoStack);
            redoStack.push(bc);
            mMain.updateRedoList(redoStack);
            mView.modelChanged();
        }
        mView.requestFocus();
    }

    public void handleRedo(ActionEvent event) {
        if (redoStack.empty()) {
            System.out.println("Nothing more to redo!");
        } else {
            BlobCommand bc = redoStack.pop();
            bc.doit();
            mMain.updateRedoList(redoStack);
            undoStack.push(bc);
            mMain.updateUndoList(undoStack);
            mView.modelChanged();
        }
        mView.requestFocus();
    }

}