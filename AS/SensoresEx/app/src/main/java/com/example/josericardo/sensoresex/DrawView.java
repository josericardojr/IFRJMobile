package com.example.josericardo.sensoresex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawView extends SurfaceView implements Runnable,
        View.OnTouchListener, SensorEventListener {

    boolean isRunning;
    SurfaceHolder surfaceHolder;
    Thread gameLoop;
    int xCircle, yCircle;
    int xAccel, yAccel, zAccel;

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){

            case Sensor.TYPE_ACCELEROMETER:
                xAccel = (int) event.values[0];
                yAccel = (int) event.values[1];
                zAccel = (int) event.values[2];
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public DrawView(Context context){
        super(context);

        surfaceHolder = getHolder();
        setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }

    public void resume(){
        isRunning = true;

        gameLoop = new Thread(this);
        gameLoop.start();

    }

    public void stop(){
        isRunning = false;

        try {
            gameLoop.join();
        } catch (InterruptedException ie){

        }
    }


    @Override
    public void run() {

        while (isRunning){

            update();
            render();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ie){

            }
        }
    }


    void update(){
    }

    void render(){

        if (!surfaceHolder.getSurface().isValid())
            return;

        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);


        float width = canvas.getWidth();
        float height = canvas.getHeight();

        float centerx = width / 2.0f;
        float centery = height / 2.0f;

        float ratio_x = width / 10.0f;
        float ratio_y = height / 10.0f;

        float x = centerx - xAccel * ratio_x;
        float y = centery + yAccel * ratio_y;

        canvas.drawCircle(x, y, 100, paint);

        surfaceHolder.unlockCanvasAndPost(canvas);

    }
}
