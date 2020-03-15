package com.example.homeyxue.graphdemo;

/**
 * Created by homeyxue on 2018-02-06.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MiniGraphView extends View implements GraphModelListener {
    Paint myPaint;
    GraphModel model;
    MainGraphView main;

    public MiniGraphView(Context aContext) {
        super(aContext);
        myPaint = new Paint();
        this.setBackgroundColor(Color.LTGRAY);
    }

    public void setModel(GraphModel aModel) {
        model = aModel;
    }

    public void setWatchedView(MainGraphView aView) { main = aView;}

    public void onDraw(Canvas c) {
        // draw main view's viewport
        myPaint.setColor(Color.GRAY);
        float viewLeft = main.portX / (float) main.getWidth() * this.getWidth();
        float viewTop = main.portY / (float) main.getHeight() * this.getHeight();
        float viewRight = (main.portX + main.visibleSize.width()) / (float) main.getWidth() * this.getWidth();
        float viewBottom = (main.portY + main.visibleSize.height()) / (float) main.getWidth() * this.getHeight();
        c.drawRect(viewLeft, viewTop, viewRight, viewBottom, myPaint);

        // draw edges
        for (Edge e : model.edges) {
            drawEdge(e, c);
        }

        // draw vertices
        for (Vertex v : model.vertices) {
            drawVertex(v, c);
        }
    }

    private void drawVertex(Vertex v, Canvas c) {
        float left = (v.x - v.radius) * getWidth();
        float top = (v.y - v.radius) * getHeight();
        float right = (v.x + v.radius) * getWidth();
        float bottom = (v.y + v.radius) * getHeight();

        myPaint.setColor(Color.BLUE);
        myPaint.setStyle(Paint.Style.FILL);
        c.drawOval(left, top, right, bottom, myPaint);
    }

    private void drawEdge(Edge e, Canvas c) {
        float x1 = e.start.x * getWidth();
        float y1 = e.start.y * getHeight();
        float x2 = e.end.x * getWidth();
        float y2 = e.end.y * getHeight();

        myPaint.setColor(Color.BLACK);
        myPaint.setStrokeWidth(2);
        c.drawLine(x1,y1,x2,y2, myPaint);
    }

    public void modelChanged() {
        invalidate();
    }
}

