package com.example.homeyxue.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.EventListener;

public class LayoutSimulator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText id =  findViewById(R.id.id1);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_simulator);
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        final LayoutView boo = new LayoutView(this);
        boo.setBackgroundColor(Color.parseColor("#2a0d45"));

        rootLayout.addView(boo);


        View.OnTouchListener mListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                float xx = motionEvent.getX();
                float yy = motionEvent.getY();

                System.out.print(xx + yy);
                EditText editText = findViewById(R.id.alignment);
                String a = /*Float.toString(xx) + */Float.toString(yy);
                char[] b = a.toCharArray();
                editText.setText(b, 0, a.length());

                EditText clickText = findViewById(R.id.width);


                if(!((xx > 100 && xx < 400) && (yy < 500 && yy > 300))){
                    char[] carr = "Ok bye".toCharArray();
                    clickText.setText(carr, 0, carr.length);
                    return false;
                }


                char[] carr = "Click!".toCharArray();
                clickText.setText(carr, 0, carr.length);

                return true;
            }


        };


        boo.setOnTouchListener(mListener);
/*
        boo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               *//* int action = motionEvent.getAction();
                if(action == MotionEvent.ACTION_DOWN){
                    EditText editText = findViewById(R.id.e5);
                    editText.setBackgroundColor(Color.YELLOW);
                    return true;
                }else if(action == MotionEvent.ACTION_DOWN){
                    EditText editText = findViewById(R.id.e5);
                    editText.setBackgroundColor(Color.YELLOW);
                    return true;
                }else if(action == MotionEvent.ACTION_MOVE){
                    EditText editText = findViewById(R.id.e5);
                    editText.setBackgroundColor(Color.YELLOW);
                    return true;
                }*//*
                return false;
            }
        });*/
    }


    public void sendMessage(View view) {
        // Do something in response to button
    }



}
