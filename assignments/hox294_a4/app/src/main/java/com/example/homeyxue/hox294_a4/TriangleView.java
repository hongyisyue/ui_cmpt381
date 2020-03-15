package com.example.homeyxue.hox294_a4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by homeyxue on 2018-02-27.
 */

public class TriangleView extends View implements TriangleModelListener{

    Paint myPaint;
    TriangleModel myModel;
    TriangleViewController myController;
    InteractionModel iModel;


    public TriangleView(Context aContext, AttributeSet attr){
        super(aContext,attr);
        myPaint = new Paint();
        setBackgroundColor(Color.rgb(65, 30, 90));
    }

    public void setModels(TriangleModel aModel,InteractionModel iModel){
        myModel = aModel;
        this.iModel = iModel;
    }

    public void setController(TriangleViewController aController){
        myController = aController;
    }

    @Override
    protected void onDraw(Canvas c){

        for (Triangle t : myModel.triangles){
            drawTriangle(t, c);
        }
        if(iModel.selected!=null){
            drawSelected(iModel.selected, c);
        }

    }

    private void drawTriangle(Triangle aTriangle, Canvas c){
        myPaint.setColor(Color.rgb(50, 120, 120));
        myPaint.setStyle(Paint.Style.FILL);
        aTriangle.drawNormal(c, myPaint);
    }

    private void drawSelected(Triangle selected, Canvas c){
        myPaint.setColor(Color.rgb(220, 40, 200));
        myPaint.setStyle(Paint.Style.FILL);
        selected.drawNormal(c, myPaint);
        selected.drawBBX(c, myPaint);
        myPaint.setColor(Color.WHITE);
        myPaint.setStrokeWidth(2);
        myPaint.setTextSize(35);
        c.drawText("TX: "+ String.format("%.0f",selected.transX), 10, 40, myPaint);
        c.drawText("TY: " + String.format("%.0f", selected.transY), 10, 80, myPaint);
        c.drawText("SX: "+ String.format("%.10f",selected.scaleX), 10, 120, myPaint);
        c.drawText("SY: " + String.format("%.10f",selected.scaleY), 10, 160, myPaint);
        c.drawText("Rotate: "+ String.format("%.10f",selected.rotation*100), 10, 200, myPaint);
    }

    public void modelChanged() {
        invalidate();
    }
}
