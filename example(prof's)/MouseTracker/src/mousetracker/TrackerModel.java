/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetracker;

import java.util.ArrayList;

/**
 *
 * @author gutwin
 */
public class TrackerModel {

    int numberOfClicks;
    double totalMovement;
    ArrayList<TrackerModelListener> subscribers;

    public TrackerModel() {
        numberOfClicks = 0;
        totalMovement = 0;
        subscribers = new ArrayList<>();
    }

    public void addClick() {
        numberOfClicks++;
        notifySubscribers();
    }

    public void addMovement(double distance) {
        totalMovement += distance;
        notifySubscribers();
    }

    public void addSubscriber(TrackerModelListener aSubscriber) {
        subscribers.add(aSubscriber);
    }

    public void removeSubscriber(TrackerModelListener aSubscriber) {
        subscribers.remove(aSubscriber);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
