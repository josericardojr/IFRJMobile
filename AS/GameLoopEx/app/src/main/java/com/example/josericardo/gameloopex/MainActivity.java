package com.example.josericardo.gameloopex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
