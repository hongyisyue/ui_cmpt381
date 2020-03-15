package com.example.homeyxue.graphdemo;

/**
 * Created by homeyxue on 2018-02-06.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class MainGraphView extends View implements GraphModelListener {
    Paint myPaint;
    GraphModel model;

    Vertex selected = null;
    Vertex edgeSource = null;
    float lineEndX, lineEndY;
    float portX, portY;
    Rect visibleSize;
    MiniGraphView mini;

    public MainGraphView(Context aContext) {
        super(aContext);
        myPaint = new Paint();
        this.setBackgroundColor(Color.GRAY);
        visibleSize = new Rect();
    }

    public void setModel(GraphModel aModel) {
        model = aModel;
    }

    public void setMiniView(MiniGraphView aView) {
        mini = aView;
    }

    public void onDraw(Canvas c) {
        // record my visible size if not already done
        if (visibleSize.height() == 0) {
            getLocalVisibleRect(visibleSize);
        }

        // draw edges
        for (Edge e : model.edges) {
            drawEdge(e, c);
        }

        // draw temporary edge if there is one being drawn
        drawTemporaryEdge(c);

        // draw vertices
        for (Vertex v : model.vertices) {
            drawVertex(v, c);
        }
    }

    private void drawVertex(Vertex v, Canvas c) {
        // model coordinates are 0.0 - 1.0, convert to view coordinates
        float left = (v.x - v.radius) * getWidth() - portX;
        float top = (v.y - v.radius) * getHeight() - portY;
        float right = (v.x + v.radius) * getWidth() - portX;
        float bottom = (v.y + v.radius) * getHeight() - portY;

        if (v == selected) {
            // draw selected vertex in orange
            myPaint.setColor(Color.rgb(220, 180, 50));
            c.drawOval(left, top, right, bottom, myPaint);
            if (v == edgeSource) {
                // if selected is also starting an edge, draw black border
                myPaint.setColor(Color.BLACK);
                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(3);
                c.drawOval(left, top, right, bottom, myPaint);
            }
        } else {
            // draw regular vertices in blue
            myPaint.setColor(Color.BLUE);
            myPaint.setStyle(Paint.Style.FILL);
            c.drawOval(left, top, right, bottom, myPaint);
        }

        // draw label (id number)
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(48);
        myPaint.setColor(Color.WHITE);
        c.drawText("V" + v.id, left + (right - left) / 2, top + (bottom - top) / 2, myPaint);
    }

    private void drawEdge(Edge e, Canvas c) {
        // model coordinates are 0.0 - 1.0, convert to view coordinates
        float x1 = e.start.x * getWidth() - portX;
        float y1 = e.start.y * getHeight() - portY;
        float x2 = e.end.x * getWidth() - portX;
        float y2 = e.end.y * getHeight() - portY;

        myPaint.setColor(Color.BLACK);
        c.drawLine(x1, y1, x2, y2, myPaint);
    }

    private void drawTemporaryEdge(Canvas c) {
        if (edgeSource != null) {
            float x1 = edgeSource.x * getWidth() - portX;
            float y1 = edgeSource.y * getHeight() - portY;
            myPaint.setColor(Color.BLACK);
            myPaint.setStrokeWidth(3);
            c.drawLine(x1, y1, lineEndX, lineEndY, myPaint);
        }
    }

    public void modelChanged() {
        invalidate();
    }

    public void setSelected(Vertex newSelected) {
        selected = newSelected;
        invalidate();
    }

    public void setEdgeSource(Vertex newSource) {
        edgeSource = newSource;
        invalidate();
    }

    public void setLineEnd(float newX, float newY) {
        lineEndX = newX;
        lineEndY = newY;
        invalidate();
    }

    public void moveViewport(float dx, float dy) {
        // viewport moves happen in view coordinates, not model coordinates
        portX -= dx;
        portY -= dy;
        if (portX < 0) portX = 0;
        if (portY < 0) portY = 0;
        if (portX > getWidth() - visibleSize.width()) portX = getWidth() - visibleSize.width();
        if (portY > getHeight() - visibleSize.height()) portY = getHeight() - visibleSize.height();

        invalidate();
        mini.invalidate();
    }
}
