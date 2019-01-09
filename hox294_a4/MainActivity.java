package com.example.homeyxue.hox294_a4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TriangleModel model = new TriangleModel();
        model.createTriangle(100,100,150,350,300, 200);
        model.createTriangle(300, 1100, 300, 1300, 500, 1300);
        model.createTriangle(500, 700, 600, 900, 900, 800);

        InteractionModel iModel = new InteractionModel();

        TriangleViewController controller = new TriangleViewController();

        TriangleView view = new TriangleView(this,null);
        view.setModels(model,iModel);
        iModel.addSubscriber(view);
        model.addSubscriber(view);

        view.setController(controller);
        view.setOnTouchListener(controller);

        controller.setModel(model);
        controller.setView(view);
        controller.setInteractionModel(iModel);

        setContentView(view);
    }
}
