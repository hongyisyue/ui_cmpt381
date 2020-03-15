package com.example.homeyxue.graphdemo;

/**
 * Created by homeyxue on 2018-02-06.
 */

import android.view.MotionEvent;
import android.view.View;

public class MainGraphViewController implements View.OnTouchListener, View.OnLongClickListener  {
    GraphModel model;
    MainGraphView view;

    int state;
    final int STATE_READY = 0;
    final int STATE_VERTEX_ARMED = 1;
    final int STATE_DRAGGING = 2;
    final int STATE_DRAWING_EDGE = 3;
    final int STATE_SWIPE = 4;
    final int STATE_BACKGROUND = 5;

    Vertex currentVertex;
    float modelX, modelY;
    float touchX, touchY;
    float prevModelX, prevModelY;
    float prevTouchX, prevTouchY;
    float modelDX, modelDY;
    float touchDX, touchDY;

    public MainGraphViewController() {
        currentVertex = null;
        model = null;
        state = STATE_READY;
    }

    public void setModel(GraphModel aModel) {
        model = aModel;
    }

    public void setView(MainGraphView mgv) {
        view = mgv;
    }

    @Override
    public boolean onLongClick(View v) {
        if (state == STATE_VERTEX_ARMED) {
            view.setEdgeSource(currentVertex);
            view.setLineEnd(prevTouchX, prevTouchY);
            state = STATE_DRAWING_EDGE;
        }
        return true;
    }

    public boolean onTouch(View v, MotionEvent event) {
        // calculate movement amounts in model coordinates (between 0.0 and 1.0)
        modelX = (event.getX() + view.portX) / view.getWidth();
        modelY = (event.getY() + view.portY) / view.getHeight();
        modelDX = modelX - prevModelX;
        modelDY = modelY - prevModelY;
        prevModelX = modelX;
        prevModelY = modelY;

        // movement amounts in view coordinates (between 0 and view.getWidth())
        touchX = event.getX();
        touchY = event.getY();
        touchDX = touchX - prevTouchX;
        touchDY = touchY - prevTouchY;
        prevTouchX = touchX;
        prevTouchY = touchY;

        switch (state) {
            case STATE_READY:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // prepare for drag or a long press if we are on a vertex,
                        // or a swipe if we are on the background
                        if (model.contains(modelX, modelY)) {
                            currentVertex = model.findClick(modelX, modelY);
                            view.setSelected(currentVertex);
                            state = STATE_VERTEX_ARMED;
                        } else {
                            // on background, so start swipe or create
                            state = STATE_BACKGROUND;
                        }
                        break;
                }
                break;

            case STATE_BACKGROUND:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // this is a creation action
                        model.createVertex(modelX, modelY);
                        state = STATE_READY;
                        view.setSelected(null);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // this is a swipe
                        state = STATE_SWIPE;
                        view.moveViewport(touchDX, touchDY);
                        break;
                }
                break;

            case STATE_VERTEX_ARMED:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // switch to dragging and start moving
                        state = STATE_DRAGGING;
                        model.moveVertex(currentVertex, modelDX, modelDY);
                        break;
                    case MotionEvent.ACTION_UP:
                        // no drag started, and no long press occurred, so no-op
                        currentVertex = null;
                        state = STATE_READY;
                        view.setSelected(null);
                        break;
                }
                break;

            case STATE_DRAGGING:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // continue dragging vertex
                        model.moveVertex(currentVertex, modelDX, modelDY);
                        break;
                    case MotionEvent.ACTION_UP:
                        // drag finished
                        currentVertex = null;
                        state = STATE_READY;
                        view.setSelected(null);
                        break;
                }
                break;

            case STATE_DRAWING_EDGE:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // continue temporary edge line
                        view.setLineEnd(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_UP:
                        // temp edge finished, check for hit on vertex; if so, create edge
                        if (model.contains(modelX, modelY)) {
                            model.createEdge(currentVertex, model.findClick(modelX, modelY));
                        }
                        currentVertex = null;
                        view.setSelected(null);
                        view.setEdgeSource(null);
                        state = STATE_READY;
                        break;
                }
                break;

            case STATE_SWIPE:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // continue swipe
                        view.moveViewport(touchDX, touchDY);
                        break;
                    case MotionEvent.ACTION_UP:
                        // swipe finished
                        state = STATE_READY;
                        break;
                }
                break;
        }
        // don't consume the event, since long press handler needs it too
        return false;
    }
}