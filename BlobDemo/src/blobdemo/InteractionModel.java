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

public class InteractionModel {

    //Blob selected;
    ArrayList<Groupable> selectionSet;
    ArrayList<ModelListener> subscribers;
    boolean controlDown;
    RubberRectangle rubber;

    public InteractionModel() {
        //selected = null;
        selectionSet = new ArrayList<>();
        subscribers = new ArrayList<>();
        controlDown = false;
        rubber = null;
    }

    public void setControl(boolean isDown) {
        controlDown = isDown;
        notifySubscribers();
    }

    public boolean isSelected(Groupable b) {
        return selectionSet.contains(b);
    }

    public void addSubtractSelection(Groupable b) {
        if (selectionSet.contains(b)) {
            selectionSet.remove(b);
        } else {
            selectionSet.add(b);
        }
        notifySubscribers();
    }

    public void addSubtractSelection(List<Groupable> set) {
        set.forEach(b -> addSubtractSelection(b));
    }

    public void clearSelection() {
        selectionSet.clear();
        notifySubscribers();
    }

    public boolean hasRubberband() {
        return (rubber != null);
    }

    public void deleteRubber() {
        rubber = null;
        notifySubscribers();
    }

    public void setRubberbandStart(double x1, double y1) {
        rubber = new RubberRectangle(x1, y1);
    }

    public void setRubberbandEnd(double x2, double y2) {
        rubber.updateCoords(x2, y2);
        notifySubscribers();
    }

    public void addSubscriber(ModelListener aSubscriber) {
        subscribers.add(aSubscriber);
    }

    public void removeSubscriber(ModelListener aSubscriber) {
        subscribers.remove(aSubscriber);
    }

    private void notifySubscribers() {
        subscribers.forEach((sub) -> sub.modelChanged());
    }
}

