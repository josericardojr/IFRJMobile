package com.example.josericardo.singletouchex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        setContentView(drawView);
    }

    @Override
    protected void onStop() {
        super.onStop();

        drawView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawView.resume();
    }
}
