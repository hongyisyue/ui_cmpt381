package com.example.homeyxue.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by homeyxue on 2018-02-04.
 */

public class XFrame extends XWidget {
    String Orientation;        // "HORIZONTAL"  "VERTICAL"
    //Paint p = new Paint();

    LinkedList<XWidget> ChildList;

    public XFrame(int id) {
        this.id = id;
        ChildList = new LinkedList<>();
    }

    public void layout(float X, float Y, float width, float height){
        positionX = X;
        positionY = Y;
        preferWid = width;
        preferHei = height;
    }

    /**
    public void draw(Canvas canvas){
        p.setColor(Color.GREEN);
        canvas.drawRect(positionX,positionY,preferWid,preferHei,p);
    }
     */

    public void setOrientation(String orientationType){
        Orientation = orientationType;
    }

    public void addChild(XWidget XChild){
        ChildList.add(XChild);
    }

    public float childlength(){
        float divide = 1/ChildList.size();
        float widPreChild;

        if(Orientation == "VERTICAL") {

            widPreChild = divide * preferHei;
            return widPreChild;
        }

        else{
            widPreChild = divide * preferWid;
            return widPreChild;
        }
    }


}
