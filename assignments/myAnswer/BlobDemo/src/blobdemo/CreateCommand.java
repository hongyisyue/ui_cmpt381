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
public class CreateCommand implements BlobCommand{
    Groupable item;
    double blobX, blobY;

    BlobModel bModel;

    public CreateCommand(BlobModel aModel, Groupable aitem, double newX, double newY){
        bModel = aModel;
        blobX = newX;
        blobY = newY;

        item = aitem;
    }
    @Override
    public void doit() {
        bModel.addBlob(item);
    }

    @Override
    public void undo() {
        if(item.hasChildren()){
            System.out.println(item.getChildren());
            ArrayList<Groupable> items = item.getChildren();
            bModel.deleteGroups(items);
        }
        bModel.deleteGroup(item);
    }

    public String toString(){
        return "Create: " + (int)blobX + " , " + (int)blobY;
    }
}
