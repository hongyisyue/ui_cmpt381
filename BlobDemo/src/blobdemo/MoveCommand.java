/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobdemo;

import java.util.ArrayList;

/**
 * Created by homeyxue on 2018-04-02.
 */
public class MoveCommand implements BlobCommand{

    ArrayList<Groupable> groups;
    BlobModel model;
    double dx, dy;

    public MoveCommand(BlobModel aModel, ArrayList<Groupable> newGroups, double newDX, double newDY) {
        model = aModel;
        groups = (ArrayList<Groupable>)newGroups.clone();
        dx = newDX;
        dy = newDY;
    }

    @Override
    public void doit() {
        model.moveBlobs(groups, dx, dy);
    }

    @Override
    public void undo() {
        model.moveBlobs(groups, -dx, -dy);
    }

    public String toString() {
        return "Move: " + (int)dx + " , " + (int)dy;
    }

}

