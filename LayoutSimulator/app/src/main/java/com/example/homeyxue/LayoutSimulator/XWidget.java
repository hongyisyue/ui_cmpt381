package com.example.homeyxue.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by homeyxue on 2018-02-04.
 */

public abstract class XWidget{

    protected int id;

    protected float preferWid;

    protected float preferHei;

    protected int insets;           // how much padding will be used around the widget in its allocated area

    protected float positionX;     //x value of left upper point

    protected float positionY;     //y value of left upper point

    private Paint p = new Paint();

    public XWidget() {
        p.setColor(Color.GREEN);
    }

    /**
    public void Draw(Canvas canvas, int wid, int height){
        canvas.drawRect(100,300,400,500,p);
    }
     */
}
