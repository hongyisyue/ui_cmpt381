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
import java.util.stream.Collectors;

public class BlobModel {

    ArrayList<Groupable> blobs;
    ArrayList<ModelListener> subscribers;
    int nextZ;

    public BlobModel() {
        blobs = new ArrayList<>();
        subscribers = new ArrayList<>();
        nextZ = 0;
    }

    public List<Groupable> getBlobs() {
        return blobs;
    }

    public void addBlob(double x, double y) {
        blobs.add(new Blob(x, y, 15, nextZ++));
        notifySubscribers();
    }

    public void addBlob(Groupable aBlob){
        blobs.add(aBlob);
        notifySubscribers();
    }

    public Groupable createGroup(ArrayList<Groupable> ng){
        Group newGroup = new Group();
        for(Groupable g : ng){
            blobs.remove(g);
            newGroup.add(g);
        }
        blobs.add(newGroup);
        notifySubscribers();
        return newGroup;
    }

    public void addGroup(ArrayList<Groupable> aGroup){
        for (Groupable g: aGroup){
            blobs.add(g);
        }
        notifySubscribers();
    }

    public ArrayList<Groupable> ungroup(Groupable ug){
        ArrayList<Groupable> unGroupItem = new ArrayList<>();
        for(Groupable g: ug.getChildren()){
            unGroupItem.add(g);
            blobs.add(g);
        }
        blobs.remove(ug);
        notifySubscribers();
        return unGroupItem;
    }

    public void addSubscriber(ModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public void modelChanged(){notifySubscribers();}

    public boolean contains(double x, double y) {
        return blobs.stream()
                .anyMatch(blob -> blob.contains(x, y));
    }

    public Optional<Groupable> find(double x, double y) {
        return blobs.stream()
                .filter(blob -> blob.contains(x, y))
                .reduce((b1, b2) -> b2);
        //.findFirst();
    }

    public List<Groupable> findRubber(double x1, double y1, double x2, double y2) {
        return blobs.stream()
                .filter(blob -> blob.isContained(x1, y1, x2, y2))
                .collect(Collectors.toList());
    }

    public void moveBlobs(List<Groupable> blobs, double dx, double dy) {
        blobs.forEach(blob -> blob.moveGroup(dx, dy));
        notifySubscribers();
    }

    public void raiseBlob(Groupable b) {
        int newZ = nextZ++;
        b.setZ(newZ);
        //b.zOrder = nextZ++;
        blobs.sort((b1, b2) -> b1.getZ() - b2.getZ());
        notifySubscribers();
    }

    public void deleteGroups(ArrayList<Groupable> delete){
        for(Groupable g: delete){
            deleteGroup(g);
        }
        notifySubscribers();
    }

    public void deleteGroup(Groupable deleteItem){
        if(blobs.contains(deleteItem)){
            blobs.remove(deleteItem);
        } else{
            System.out.println("ERROR: delete item is not in model!");
        }
        notifySubscribers();
    }

    public void pasteGroup(ArrayList<Groupable> paste){
        for(Groupable g: paste){
            blobs.add(g);
        }
        notifySubscribers();
    }
}