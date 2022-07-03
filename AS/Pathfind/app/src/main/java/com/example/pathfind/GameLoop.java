package com.example.pathfind;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.concurrent.LinkedBlockingQueue;

/*
 * Abstract class that represents a functional game loop.
 * All mechanics and rules in the game be implemented in a subclass by using the abstract methods
 */
public abstract class GameLoop extends SurfaceView implements Runnable, View.OnTouchListener {

    volatile boolean isRunning = false;
    SurfaceHolder surfaceHolder = null;
    Thread gameLoop = null;

    // Desired Frame Rate
    public static final int FRAME_RATE = 60;
    public static final float SEC_PER_FRAME = 1.0f / (float) FRAME_RATE;

    LinkedBlockingQueue<MotionEvent> motionEventQueue = new LinkedBlockingQueue<>();


    public GameLoop(Context context, AttributeSet attrs){
        super(context, attrs);
        surfaceHolder = getHolder();
        setOnTouchListener(this);
    }

    public GameLoop(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        surfaceHolder = getHolder();
        setOnTouchListener(this);
    }

    public GameLoop(Context context){
        super(context);

        surfaceHolder = getHolder();
        setOnTouchListener(this);
    }

    public void stop(){
        isRunning = false;

        try {
            gameLoop.join();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }


    public void resume(){
        isRunning = true;

        gameLoop = new Thread(this);
        gameLoop.start();

    }

    @Override
    public void run() {
        long previous = System.currentTimeMillis();
        float totalElapsed = 0;

        while (isRunning){
            long current = System.currentTimeMillis();
            float elapsed = (float) (current - previous) / 1000.0f;
            previous = current;

            processInput(motionEventQueue);
            onUpdate(elapsed);
            totalElapsed += elapsed;

            if (totalElapsed > SEC_PER_FRAME){
                render();
                totalElapsed -= SEC_PER_FRAME;

                if (totalElapsed > SEC_PER_FRAME){
                    Log.d("GLI",
                            "Performance Warning (rendering or update taking too long!)");
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /*
     * This method is called in order to process all inputs that have been performed in the
     * last frame. At the end of the frame, all events are removed.
     */
    public abstract void processInput(LinkedBlockingQueue<MotionEvent> eventQueue);

    /*
     * Method called for performing a non fixed update. Called as fast as possible.
     */
    public abstract void onUpdate(float dt);

    /*
     * This method is responsible for drawing on the canvas. While inside this method,
     * the screen canvas is guarantee to be locked.
     */
    public abstract void onRender(Canvas canvas);

    private void render(){
        if (!surfaceHolder.getSurface().isValid())
            return;

        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        onRender(canvas);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        motionEventQueue.add(motionEvent);
        return true;
    }
}
