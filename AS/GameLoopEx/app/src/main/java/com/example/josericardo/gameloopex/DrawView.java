package com.example.josericardo.gameloopex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawView extends SurfaceView implements Runnable {

    boolean isRunning;
    SurfaceHolder surfaceHolder;
    Thread gameLoop;


    public DrawView(Context context){
        super(context);

        surfaceHolder = getHolder();
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
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(100, 100, 200, paint);
        surfaceHolder.unlockCanvasAndPost(canvas);

    }
}
