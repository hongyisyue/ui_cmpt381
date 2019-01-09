package com.example.homeyxue.hox294_a4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by homeyxue on 2018-02-27.
 */

public class Triangle {

    float x0, y0, x1, y1, x2, y2;
    float realx0, realy0, realx1, realy1, realx2, realy2;

    float transX, transY;
    float scaleX, scaleY;
    float rotation;
    float factorX1, factorY2;

    float controlPointRadius;

    float bbx0,bby0, bbx1,bby1, bbx2,bby2, bbx3,bby3;
    float realbbx0, realbby0, realbbx1, realbby1, realbbx2, realbby2, realbbx3, realbby3;

    public Triangle(float newX, float newY, float newX1, float newY1, float newX2, float newY2){
        controlPointRadius = 10;

        x0 = newX;
        y0 = newY;  //upper-left
        x1 = newX1;
        y1 = newY1; //bottom
        x2 = newX2;
        y2 = newY2; //right

        transX =0;
        transY =0;
        scaleX =1;
        scaleY =1;
        rotation =0;

        bbx0 = x0;  //up left
        bby0 = y0;

        bbx1 = x2;  //up right
        bby1 = y0;

        bbx2 = x0;  //low left
        bby2 = y1;

        bbx3 = x2;  //low right
        bby3 = y1;

        factorX1 = (x1 - bbx2)/(bbx3 - bbx2);
        factorY2 = (y2 - bby1)/(bby3 - bby1);
    }

    public void setTranslate(float tX, float tY){
        transX = tX;
        transY = tY;
    }

    public void setScale(float sX, float sY){
        scaleX = sX;
        scaleY = sY;
    }

    public void setAngle(float radians){
        rotation = radians;
    }

    public float sign(float x1, float y1, float x2, float y2, float x3, float y3){

        return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
    }

    /**
     * To determine if a point is in the triangle
     * @param mouseX
     * @param mouseY
     * @return
     */
    public boolean contains(float mouseX, float mouseY){
        boolean b1, b2, b3;

        b1 = sign(mouseX, mouseY, x0, y0, x1, y1) < 0.0f;
        b2 = sign(mouseX, mouseY, x1, y1, x2, y2) < 0.0f;
        b3 = sign(mouseX, mouseY, x2, y2, x0, y0) < 0.0f;

        return ((b1 == b2) && (b2 == b3));
    }

    /**
     * to determine if the mouse is clicking on green button
     * @param mouseX
     * @param mouseY
     * @return
     */
    public boolean onGreenButton(float mouseX, float mouseY){
        if((float)Math.sqrt(Math.pow((double)mouseX-(double)bbx3,2) + Math.pow((double)mouseY-(double)bby3,2)) <= controlPointRadius){
            return true;
        }else {
            return false;
        }
    }

    public boolean onYellowButton(float mouseX, float mouseY){
        if((float)Math.sqrt(Math.pow((double)mouseX-(double)bbx0,2) + Math.pow((double)mouseY-(double)bby0,2)) <= controlPointRadius){
            return true;
        }else {
            return false;
        }
    }

    public void drawNormal(Canvas c, Paint p){

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.rewind();
        path.moveTo(x0,y0);
        path.lineTo(x1,y1);
        path.lineTo(x2,y2);
        path.close();
        c.drawPath(path, p);

    }

    public void drawBBX(Canvas c, Paint p){

        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.FILL);
        c.drawCircle(bbx0, bby0, controlPointRadius, p);
        p.setColor(Color.LTGRAY);
        p.setStyle(Paint.Style.STROKE);
        c.drawLine(bbx0, bby0, bbx1, bby1, p);
        c.drawLine(bbx1, bby1, bbx3, bby3, p);
        c.drawLine(bbx3, bby3, bbx2, bby2, p);
        c.drawLine(bbx2, bby2, bbx0, bby0, p);
        p.setColor(Color.GREEN);
        p.setStyle(Paint.Style.FILL);
        c.drawCircle(bbx3, bby3, controlPointRadius, p);

    }
}
