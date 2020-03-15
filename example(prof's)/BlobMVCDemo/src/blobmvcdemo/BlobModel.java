/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobmvcdemo;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author gutwin
 */
public class BlobModel {

    ArrayList<Blob> blobs;
    ArrayList<BlobModelListener> subscribers;
    int nextZ;

    public BlobModel() {
        blobs = new ArrayList<>();
        subscribers = new ArrayList<>();
        nextZ = 0;
    }

    public void addBlob(double x, double y) {
        blobs.add(new Blob(x, y, 15, nextZ++));
        notifySubscribers();
    }

    public void addSubscriber(BlobModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    private void notifySubscribersMove(double dx, double dy) {
        subscribers.forEach(sub -> sub.modelChanged(dx,dy));
    }

    public boolean contains(double x, double y) {
        return blobs.stream()
                .anyMatch(blob -> blob.contains(x, y));
    }

    public boolean containsOld(double x, double y) {
        boolean found = false;
        for (Blob b : blobs) {
            if (b.contains(x, y)) {
                found = true;
            }
        }
        return found;
    }
    
    public Optional<Blob> find(double x, double y) {
        return blobs.stream()
                .filter(blob -> blob.contains(x, y))
                .reduce((b1,b2) -> b2);
                //.findFirst();
    }
            
    public Blob findOld(double x, double y) {
        Blob target = null;
        for (Blob b : blobs) {
            if (b.contains(x, y)) {
                target = b;
            }
        }
        return target;
    }
    
    public void moveBlob(Blob b, double dx, double dy) {
        if (b != null) {
            b.x += dx;
            b.y += dy;
        }
        notifySubscribersMove(dx, dy);
    }
    
    public void raiseBlob(Blob b) {
        if (nextZ == Integer.MAX_VALUE) {
            nextZ = 0;
            blobs.forEach(blob -> blob.zOrder = nextZ++);
        } else {
            b.zOrder = nextZ++;
        }
        blobs.sort((b1, b2) -> b1.zOrder - b2.zOrder);
        notifySubscribersMove(0,0);
    }
}
