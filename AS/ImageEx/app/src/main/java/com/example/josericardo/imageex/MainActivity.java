package com.example.josericardo.imageex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public class DrawView extends View {

        Bitmap bmp;
        DisplayMetrics displayMetrics;
        float posX, posY;
        Canvas canvas;

        public DrawView(Context context) {
            super(context);

            bmp = BitmapFactory.decodeResource(getResources(),
                    R.drawable.dragon);

            displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            posX = 100;
            posY = 100;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            this.canvas = canvas;
            super.onDraw(canvas);


            canvas.drawBitmap(bmp, posX, posY, null);
            posX += 200;
            posY += 100;
        }
    }


    DrawView drawView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        setContentView(drawView);

    }
}
