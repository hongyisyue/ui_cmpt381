/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

import java.util.ArrayList;
/**
 * Created by homeyxue on 2018-03-31.
 */
public class BlobClipboard {

    ArrayList<Groupable> group;
    public BlobClipboard(){
        group = new ArrayList<>();
    }

    //deep copy
    public void copy(ArrayList<Groupable> gs) {
        group.clear();
        System.out.println("about to add list of " + gs.size() + " to clip");

        for (Groupable g : gs) {
            if (g.hasChildren()) {
                // copy a group
                group.add(new Group(g));
            } else {
                // copy a blob
                group.add(new Blob(g));
            }
        }
    }

    //shallow copy
    public void cut(ArrayList<Groupable> gs) {
        group.clear();
        group = (ArrayList<Groupable>)gs.clone();
    }

    public ArrayList<Groupable> paste() {
        ArrayList<Groupable> copy = new ArrayList<>();
        for (Groupable g : group) {
            if (g.hasChildren()) {
                // group
                copy.add(new Group(g));
            } else {
                // box
                copy.add(new Blob(g));
            }
        }
        return copy;
    }

    public String result(){
        if(group.size()>0){
            return Integer.toString(group.size())+" items";
        }else{
            return "empty";
        }
    }

}

