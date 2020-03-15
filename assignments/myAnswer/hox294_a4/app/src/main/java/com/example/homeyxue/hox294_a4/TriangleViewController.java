package com.example.homeyxue.hox294_a4;

import android.view.MotionEvent;
import android.view.View;

import java.util.Optional;

/**
 * Created by homeyxue on 2018-02-27.
 */

public class TriangleViewController implements View.OnTouchListener{
    TriangleModel mModel;
    TriangleView mView;
    InteractionModel iModel;

    float prevX=0;
    float prevY=0;
    float dX, dY;

    private final int STATE_READY = 0;
    private final int STATE_READY_TO_OPERATE = 1;
    private final int STATE_MOVING = 2;
    private final int STATE_SCALING = 3;
    private final int STATE_ROTATION = 4;

    int state = STATE_READY;

    public void setView(TriangleView view) {
        mView = view;
    }

    public void setModel(TriangleModel model) {
        mModel = model;
    }

    public void setInteractionModel(InteractionModel imodel){
        iModel = imodel;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (state){
            case  STATE_READY:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Optional<Triangle> maybeTriangle = mModel.findClickedVertex(event.getX(), event.getY());
                        if(maybeTriangle.isPresent()){
                                iModel.setSelected(maybeTriangle.get());
                                prevX = event.getX();
                                prevY = event.getY();
                                state = STATE_READY_TO_OPERATE;
                        } else {
                            prevX = event.getX();
                            prevY = event.getY();
                            state = STATE_READY;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_READY;
                        break;
                }
                break;
            case  STATE_READY_TO_OPERATE:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(iModel.selected.contains(event.getX(), event.getY())){
                            prevX = event.getX();
                            prevY = event.getY();
                            state = STATE_MOVING;
                        }
                        else if(iModel.selected.onYellowButton(event.getX(), event.getY())){
                            prevX = event.getX();
                            prevY = event.getY();
                            state = STATE_SCALING;
                        }
                        else if(iModel.selected.onGreenButton(event.getX(), event.getY())){
                            prevX = event.getX();
                            prevY = event.getY();
                            state = STATE_ROTATION;
                        }
                        else{
                            prevX = event.getX();
                            prevY = event.getY();
                            iModel.setSelected(null);
                            state = STATE_READY;
                        }
                        break;

                        case MotionEvent.ACTION_UP:
                            prevX = event.getX();
                            prevY = event.getY();
                            state = STATE_READY_TO_OPERATE;
                        break;
                }
                break;
            case STATE_MOVING:
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
//                        iModel.selected.transX = event.getX();
//                        iModel.selected.transY = event.getY();

                        iModel.selected.setTranslate(event.getX(), event.getY());

                        dX = event.getX() - prevX;
                        dY = event.getY() - prevY;
                        mModel.moveTriangle(iModel.selected, dX, dY);
                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_MOVING;
                        break;
                    case MotionEvent.ACTION_UP:
                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_READY_TO_OPERATE;
                }
                break;
            case STATE_SCALING:
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        float centerX = (iModel.selected.bbx0+iModel.selected.bbx3) / 2;
                        float centerY = (iModel.selected.bby3+iModel.selected.bby0) / 2;

//                        iModel.selected.scaleX = (centerX-event.getX()) / (centerX - prevX);
//                        iModel.selected.scaleY = (centerY-event.getY()) / (centerY - prevY);

                        iModel.selected.setScale((centerX-event.getX()) / (centerX - prevX), (centerY-event.getY()) / (centerY - prevY));

                        mModel.scaling(iModel.selected);
                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_SCALING;
                        break;
                    case MotionEvent.ACTION_UP:

                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_READY_TO_OPERATE;
                }
                break;
            case STATE_ROTATION:
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:

                        dX = event.getX() - prevX;
                        dY = event.getY() - prevY;

                        float centerX = (iModel.selected.bbx0+iModel.selected.bbx3) / 2;
                        float centerY = (iModel.selected.bby3+iModel.selected.bby0) / 2;

                        //iModel.selected.rotation = -1*(float) Math.atan2(event.getY()-centerY, event.getX()-centerX)/(100);
//                        if((event.getX()>centerX && event.getY()>centerY && dX>0 && dY<0) || (event.getX()>centerX && event.getY()<centerY && dX<0 && dY<0) || (event.getX()<centerX && event.getY()<centerY && dX<0 && dY>0) || (event.getX()<centerX&&event.getY()>centerY &&dX>0 && dY>0)) {
//                            iModel.selected.setAngle((float) Math.atan2(event.getY() - centerY, event.getX() - centerX) / (80));
//                        }else {

//                        }
                        iModel.selected.setAngle((-1) *(float) Math.atan2(event.getY() - centerY, event.getX() - centerX) / (80));

                        System.out.println(iModel.selected.rotation*360);
                        mModel.rotation(iModel.selected);
                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_ROTATION;
                        break;
                    case MotionEvent.ACTION_UP:
                        prevX = event.getX();
                        prevY = event.getY();
                        state = STATE_READY_TO_OPERATE;
                        break;
                }

        }

        return true;
    }
}
