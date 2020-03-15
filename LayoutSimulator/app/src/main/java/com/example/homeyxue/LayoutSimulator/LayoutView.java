package com.example.homeyxue.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by homeyxue on 2018-02-01.
 */

public class LayoutView extends View {
    public int test = 100;
    private Paint p = new Paint();
    public  XFrame root;

    public XFrame vert1;
    public XFrame horiz1;

    public XButton xb1;
    public XButton xb2;
    public XButton xb3;
    public XButton xb4;
    public XButton xb5;
    public XButton xb6;


    public LayoutView(Context context) {
        super(context);
        SetColor();

        /**
        root = new XFrame(1);
        root.setOrientation("HORIZONTAL");
        vert1 = new XFrame(2);
        vert1.setOrientation("VERTICAL");
        horiz1 = new XFrame(8);
        horiz1.setOrientation("HORIZONTAL");

        xb1 = new XButton(3,200,100,"Button1");
        xb1.insets = 15;

        xb2 = new XButton(4,100,100,"Button2");
        xb2.insets = 15;

        xb3 = new XButton(5,300,100,"Button3");
        xb3.insets = 15;

        xb4 = new XButton(6,300,100,"Button4");
        xb4.insets = 15;

        xb5 = new XButton(7,300,100,"Button5");
        xb5.insets = 15;

        xb6 = new XButton(9,300,100,"Button6");
        xb5.insets = 15;

        vert1.addChild(xb1);
        vert1.addChild(xb3);
        vert1.addChild(horiz1);
        horiz1.addChild(xb2);
        horiz1.addChild(xb6);
        root.addChild(xb4);
        root.addChild(vert1);
        root.addChild(xb5);
         */
    }
    /**
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        root.layout(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        vert1.layout(root.positionX +root.childlength(), root.positionY, root.childlength(), root.preferHei);

        horiz1.layout(vert1.positionX, vert1.positionY - 2*vert1.childlength(), vert1.preferWid, vert1.childlength());

        xb1.layout(vert1.positionX, vert1.positionY, vert1.preferWid, vert1.childlength());

        xb3.layout(vert1.positionX, vert1.positionY-vert1.childlength(), vert1.preferWid, vert1.childlength());

        xb2.layout(horiz1.positionX, horiz1.positionY, horiz1.childlength(), horiz1.preferHei);

        xb6.layout(horiz1.positionX - horiz1.childlength(), horiz1.positionY, horiz1.childlength(), horiz1.preferHei);

        xb4.layout(root.positionX, root.positionY, root.childlength(), root.preferHei);

        xb5.layout(root.positionX - 2*root.childlength(), root.positionY, root.childlength(), root.preferHei);


        canvas.drawRect(root.positionX,root.positionY,root.preferWid,root.preferHei,p);
        canvas.drawRect(vert1.positionX,vert1.positionY,vert1.preferWid,vert1.preferHei,p);
        canvas.drawRect(horiz1.positionX,horiz1.positionY,horiz1.preferWid,horiz1.preferHei,p);

        canvas.drawRect(xb1.positionX,xb1.positionY,xb1.preferWid,xb1.preferHei,p);
        canvas.drawText(xb1.buttonName, xb1.positionX, xb1.positionY, p);

        canvas.drawRect(xb2.positionX,xb2.positionY,xb2.preferWid,xb2.preferHei,p);
        canvas.drawText(xb2.buttonName, xb2.positionX, xb2.positionY, p);

        canvas.drawRect(xb3.positionX,xb3.positionY,xb3.preferWid,xb3.preferHei,p);
        canvas.drawText(xb3.buttonName, xb3.positionX, xb3.positionY, p);

        canvas.drawRect(xb4.positionX,xb4.positionY,xb4.preferWid,xb4.preferHei,p);
        canvas.drawText(xb4.buttonName, xb4.positionX, xb4.positionY, p);

        canvas.drawRect(xb5.positionX,xb5.positionY,xb5.preferWid,xb5.preferHei,p);
        canvas.drawText(xb5.buttonName, xb5.positionX, xb5.positionY, p);

        canvas.drawRect(xb6.positionX,xb6.positionY,xb6.preferWid,xb6.preferHei,p);
        canvas.drawText(xb6.buttonName, xb6.positionX, xb6.positionY, p);

    }

    */
    public void onClick(View v) {
        // do something when the button is clicked
    }


    //----------- Part 1 -------------
    private void SetColor(){
        p.setColor(Color.GREEN);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect(100,300,400,500,p);
    }

    public boolean checkPosition(float x, float y) {

        return  false;
    }

}
