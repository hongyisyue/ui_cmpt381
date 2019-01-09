package com.example.homeyxue.myapplication;

import android.content.Context;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by homeyxue on 2018-02-04.
 */

public class XButton extends XWidget {
    Paint paint = new Paint();
    String buttonName;

    public XButton(int id, float width, float height, String name){
        this.id = id;
        this.preferWid = width;
        this.preferHei = height;
        this.buttonName = name;
        //this.insets = 10;
    }

    public void layout(float parentX, float parentY, float parentWid, float parentHei){

        double trueWidth = parentWid - insets*2;

        if(trueWidth > preferWid){
            positionX = parentX + (parentWid/2 - preferWid/2);
        }
        else{
            positionX = parentX + insets;
        }

        double trueHei = parentHei - insets*2;

        if(trueHei > preferHei){
            positionY = parentY + (parentHei/2 - preferHei/2);
        }
        else{
            positionY = parentY - insets;
        }
    }

}
