package com.example.josericardo.gameloopex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawView extends SurfaceView implements Runnable {

    boolean isRunning;
    SurfaceHolder surfaceHolder;
    Thread gameLoop;
    Bitmap backBufferImg;
    Canvas canvasBackbuffer;
    int x = 100, y = 100;


    public DrawView(Context context, int width, int height){
        super(context);

        surfaceHolder = getHolder();

        // 1 - Passo
        backBufferImg = Bitmap.createBitmap(
                width, height, Bitmap.Config.ARGB_8888);
        canvasBackbuffer = new Canvas(backBufferImg);
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
        x += 10;
        y += 10;
    }

    void render(){

        if (!surfaceHolder.getSurface().isValid())
            return;


        // 2 Passo
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvasBackbuffer.drawColor(Color.WHITE);
        canvasBackbuffer.drawCircle(x, y, 200, paint);


        Canvas canvas = surfaceHolder.lockCanvas();
        // 3 Passo
        canvas.drawBitmap(backBufferImg, 0, 0, null);
        surfaceHolder.unlockCanvasAndPost(canvas);

    }
}
