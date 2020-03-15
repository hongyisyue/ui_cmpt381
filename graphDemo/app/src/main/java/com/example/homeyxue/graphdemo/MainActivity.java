package com.example.homeyxue.graphdemo;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_box_demo_main);

        // Set up MVC
        MainGraphView mainView = new MainGraphView(this);
        MainGraphViewController mainController = new MainGraphViewController();
        GraphModel model = new GraphModel();
        MiniGraphView miniView = new MiniGraphView(this);

        // connections between M,V,C
        mainView.setModel(model);
        mainController.setModel(model);
        mainController.setView(mainView);
        model.addSubscriber(mainView);

        miniView.setModel(model);
        model.addSubscriber(miniView);
        miniView.setWatchedView(mainView);
        mainView.setMiniView(miniView);

        // event handling
        mainView.setOnTouchListener(mainController);
        mainView.setOnLongClickListener(mainController);

        // layout
        LinearLayout vbox1 = new LinearLayout(this);
        vbox1.setOrientation(LinearLayout.VERTICAL);
        vbox1.addView(miniView,new LinearLayout.LayoutParams(400,400));
        vbox1.addView(mainView,new LinearLayout.LayoutParams(3000,3000));
        setContentView(vbox1);
    }
}
