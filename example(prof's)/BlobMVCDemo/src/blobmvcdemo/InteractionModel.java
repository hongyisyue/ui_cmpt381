/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobmvcdemo;

import java.util.ArrayList;

/**
 *
 * @author gutwin
 */
public class InteractionModel {
    
    Blob selected;
    ArrayList<BlobModelListener> subscribers;

    public InteractionModel() {
        selected = null;
        subscribers = new ArrayList<>();
    }

    public void setSelected(Blob b) {
        selected = b;
        notifySubscribers();
    }

    public void addSubscriber(BlobModelListener aSubscriber) {
        subscribers.add(aSubscriber);
    }

    public void removeSubscriber(BlobModelListener aSubscriber) {
        subscribers.remove(aSubscriber);
    }

    private void notifySubscribers() {
        subscribers.forEach((sub) -> sub.modelChanged());
    }

}
