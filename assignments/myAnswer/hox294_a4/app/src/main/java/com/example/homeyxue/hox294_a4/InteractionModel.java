package com.example.homeyxue.hox294_a4;

import java.util.ArrayList;

/**
 * Created by homeyxue on 2018-02-27.
 */

public class InteractionModel {
    Triangle selected;
    float tempX, tempY;
    ArrayList<TriangleModelListener> subscribers;

    public InteractionModel(){
        selected = null;
        subscribers = new ArrayList<>();
    }

    public void setSelected(Triangle newSelected){
        selected = newSelected;
        notifySubscribers();
    }

    public void setTempEnd(float tX, float tY){
        tempX = tX;
        tempY = tY;
        notifySubscribers();
    }

    public void addSubscriber(TriangleModelListener aSubscriber) {
        subscribers.add(aSubscriber);
    }

    private void notifySubscribers() {
        subscribers.forEach((sub) -> sub.modelChanged());
    }
}
