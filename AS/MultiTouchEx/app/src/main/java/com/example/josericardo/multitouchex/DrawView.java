package com.example.josericardo.multitouchex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawView extends SurfaceView implements Runnable,
        View.OnTouchListener {

    boolean isRunning;
    SurfaceHolder surfaceHolder;
    Thread gameLoop;
    Point points[];
    final int MAX_CIRCLES = 5;
    boolean isVisible[];
    int colors[];



    public DrawView(Context context){
        super(context);

        surfaceHolder = getHolder();
        setOnTouchListener(this);

        points = new Point[MAX_CIRCLES];
        isVisible = new boolean[MAX_CIRCLES];
        colors = new int[MAX_CIRCLES];

        colors[0] = Color.BLACK;
        colors[1] = Color.BLUE;
        colors[2] = Color.RED;
        colors[3] = Color.GREEN;
        colors[4] = Color.MAGENTA;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int actionMask = event.getActionMasked();

        int pointerIndex = event.getActionIndex();
        int id = event.getPointerId(pointerIndex);

        switch (actionMask){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                //Log.i("PointerInfo", "Pointer Id: " + id );
                isVisible[id] = false;

                break;

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //Log.i("PointerInfo", "Pointer Id: " + id );
                if (id < MAX_CIRCLES){
                    int x = (int) event.getX(pointerIndex);
                    int y = (int) event.getY(pointerIndex);
                    points[id] = new Point();
                    points[id].x = x;
                    points[id].y = y;

                    isVisible[id] = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:

                for (int i = 0; i < event.getPointerCount(); i++){

                    int _id = event.getPointerId(i);

                    if (_id < MAX_CIRCLES){
                        int x = (int) event.getX(i);
                        int y = (int) event.getY(i);
                        points[_id].x = x;
                        points[_id].y = y;
                    }
                }
                break;
        }



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


        for (int i = 0; i < MAX_CIRCLES; i++){
            paint.setColor(colors[i]);

            if (isVisible[i])
                canvas.drawCircle(points[i].x, points[i].y, 200, paint);
        }
        surfaceHolder.unlockCanvasAndPost(canvas);

    }
}
