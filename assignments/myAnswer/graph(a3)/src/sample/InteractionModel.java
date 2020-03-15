package sample;

import java.util.ArrayList;

/**
 * Created by homeyxue on 2018-02-13.
 */
public class InteractionModel {
    Vertex selected;
    Vertex shiftSelected;
    ArrayList<listener> subscribers;

    double tempEdgeX, tempEdgeY;

    public InteractionModel() {
        selected = null;
        shiftSelected =null;
        subscribers = new ArrayList<>();
    }

    public void setSelected(Vertex v) {
        selected = v;
        notifySubscribers();
    }

    public void setShiftSelected(Vertex v){
        shiftSelected =v;
        notifySubscribers();
    }

    public void setTempEdgeEnd(double tX, double tY){
        tempEdgeX = tX;
        tempEdgeY = tY;
        notifySubscribers();
    }

    public void addSubscriber(listener aSubscriber) {
        subscribers.add(aSubscriber);
    }

    private void notifySubscribers() {
        subscribers.forEach((sub) -> sub.modelChanged());
    }

}
