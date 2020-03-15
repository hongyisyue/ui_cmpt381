package com.example.homeyxue.hox294_a4;

/**
 * Created by homeyxue on 2018-02-27.
 */

import java.util.ArrayList;
import java.util.Optional;

public class TriangleModel{
    ArrayList<Triangle> triangles;
    ArrayList<TriangleModelListener> subscribers;

    public TriangleModel(){
        triangles = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void createTriangle(float x, float y, float x1, float y1, float x2, float y2){
        Triangle aTriangle = new Triangle(x,y,x1,y1,x2,y2);
        triangles.add( aTriangle );
    }

    public void addSubscriber(TriangleModelListener aSubscriber){
        subscribers.add(aSubscriber);
    }

    private void notifySubscribers(){
        for(TriangleModelListener sub: subscribers){
            sub.modelChanged();
        }
    }

    public void rotation(Triangle t) {
        // rotation
        float centerX = (t.bbx0+t.bbx3) / 2;
        float centerY = (t.bby3+t.bby0) / 2;

        rotateHelper(t, centerX, centerY);

        t.realbbx0 = t.bbx0;
        t.realbby0 = t.bby0;
        t.realbbx1 = t.bbx1;
        t.realbby1 = t.bby1;
        t.realbbx2 = t.bbx2;
        t.realbby2 = t.bby2;
        t.realbbx3 = t.bbx3;
        t.realbby3 = t.bby3;

        t.realx0 = t.x0;
        t.realy0 = t.y0;
        t.realx1 = t.x1;
        t.realy1 = t.y1;
        t.realx2 = t.x2;
        t.realy2 = t.y2;

        t.x0 = (float) (t.realx0 * Math.cos( -1* t.rotation) - t.realy0 * Math.sin( -1* t.rotation));
        t.y0 = (float) (t.realx0 * Math.sin( -1* t.rotation) + t.realy0 * Math.cos( -1* t.rotation));
        t.x1 = (float) (t.realx1 * Math.cos( -1* t.rotation) - t.realy1 * Math.sin( -1* t.rotation));
        t.y1 = (float) (t.realx1 * Math.sin( -1* t.rotation) + t.realy1 * Math.cos( -1* t.rotation));
        t.x2 = (float) (t.realx2 * Math.cos( -1* t.rotation) - t.realy2 * Math.sin( -1* t.rotation));
        t.y2 = (float) (t.realx2 * Math.sin( -1* t.rotation) + t.realy2 * Math.cos( -1* t.rotation));

        t.bbx0 = (float) (t.realbbx0 * Math.cos( -1* t.rotation) - t.realbby0 * Math.sin( -1* t.rotation));
        t.bby0 = (float) (t.realbbx0 * Math.sin( -1* t.rotation) + t.realbby0 * Math.cos( -1* t.rotation));
        t.bbx1 = (float) (t.realbbx1 * Math.cos( -1* t.rotation) - t.realbby1 * Math.sin( -1* t.rotation));
        t.bby1 = (float) (t.realbbx1 * Math.sin( -1* t.rotation) + t.realbby1 * Math.cos( -1* t.rotation));
        t.bbx2 = (float) (t.realbbx2 * Math.cos( -1* t.rotation) - t.realbby2 * Math.sin( -1* t.rotation));
        t.bby2 = (float) (t.realbbx2 * Math.sin( -1* t.rotation) + t.realbby2 * Math.cos( -1* t.rotation));
        t.bbx3 = (float) (t.realbbx3 * Math.cos( -1* t.rotation) - t.realbby3 * Math.sin( -1* t.rotation));
        t.bby3 = (float) (t.realbbx3 * Math.sin( -1* t.rotation) + t.realbby3 * Math.cos( -1* t.rotation));

        rotateHelper(t, -centerX, -centerY);

        notifySubscribers();
    }

    public void rotateHelper(Triangle t, float dx, float dy){
        t.x0 -= dx;
        t.y0 -= dy;
        t.x1 -= dx;
        t.y1 -= dy;
        t.x2 -= dx;
        t.y2 -= dy;

        t.bbx0 -= dx;
        t.bby0 -= dy;
        t.bbx1 -= dx;
        t.bby1 -= dy;
        t.bbx2 -= dx;
        t.bby2 -= dy;
        t.bbx3 -= dx;
        t.bby3 -= dy;
    }
    public void scaling(Triangle t) {
        // scaling
        float centerX = (t.bbx0+t.bbx1) / 2;
        float centerY = (t.bby3+t.bby1) / 2;

        rotateHelper(t, centerX, centerY);

        t.bbx0 *= t.scaleX;
        t.bby0 *= t.scaleY;
        t.bbx1 *= t.scaleX;
        t.bby1 *= t.scaleY;
        t.bbx2 *= t.scaleX;
        t.bby2 *= t.scaleY;
        t.bbx3 *= t.scaleX;
        t.bby3 *= t.scaleY;

        t.x0 *= t.scaleX;
        t.y0 *= t.scaleY;
        t.x1 *= t.scaleX;
        t.y1 *= t.scaleY;
        t.x2 *= t.scaleX;
        t.y2 *= t.scaleY;

        t.realbbx0 = t.bbx0;
        t.realbby0 = t.bby0;
        t.realbbx1 = t.bbx1;
        t.realbby1 = t.bby1;
        t.realbbx2 = t.bbx2;
        t.realbby2 = t.bby2;
        t.realbbx3 = t.bbx3;
        t.realbby3 = t.bby3;

        t.realx0 = t.x0;
        t.realy0 = t.y0;
        t.realx1 = t.x1;
        t.realy1 = t.y1;
        t.realx2 = t.x2;
        t.realy2 = t.y2;

        rotateHelper(t, -centerX, -centerY);

        notifySubscribers();
    }

    public void moveTriangle(Triangle t, float dx, float dy){
        t.x0 += dx;
        t.y0 += dy;
        t.x1 += dx;
        t.y1 += dy;
        t.x2 += dx;
        t.y2 += dy;

        t.bbx0 += dx;
        t.bby0 += dy;
        t.bbx1 += dx;
        t.bby1 += dy;
        t.bbx2 += dx;
        t.bby2 += dy;
        t.bbx3 += dx;
        t.bby3 += dy;

        notifySubscribers();
    }

    public Optional<Triangle> findClickedVertex(float x, float y){
        return triangles.stream()
                .filter(tri -> tri.contains(x, y))
                .reduce((b1, b2) -> b2);
    }

    public boolean contains(float x, float y) {
        return triangles.stream()
                .anyMatch(tri -> tri.contains(x, y));
    }

}
