package com.josericardojr.snakeapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.Random;

public class Engine extends SurfaceView implements Runnable {

    private Context context;
    private Point size;
    private int blockSize;
    private int numBlocksHeight;
    private int snakeLenght;
    Thread gameLoop;
    private long nextFrame;

    private int[] snakeX;
    private int[] snakeY;
    private int score;
    private int foodX, foodY;

    public enum Heading {UP, RIGHT, DOWN, LEFT}
    private Heading heading = Heading.RIGHT;


    private SurfaceHolder surfaceHolder;
    private Paint paint;

    private boolean isPlaying = false;

    private final static int NUM_BLOCKS_WIDE = 40;
    private final static int FPS = 5;
    private final static int MILLIS_PER_SECOND = 1000;

    public Engine(Context context, Point size){
        super(context);

        this.context = context;
        this.size = size;

        blockSize = size.x / NUM_BLOCKS_WIDE;
        numBlocksHeight = size.y / blockSize;


        surfaceHolder = getHolder();
        paint = new Paint();

        snakeX = new int[200];
        snakeY = new int[200];

        newGame();
    }

    @Override
    public void run() {

        while (isPlaying){

            if (updateRequired()) {
                update();
                draw();
            }
        }
    }

    public void pause(){
        isPlaying = false;

        try {
            gameLoop.join();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;

        gameLoop = new Thread(this);
        gameLoop.start();
    }

    private void newGame(){
        snakeLenght = 1;

        snakeX[0] = NUM_BLOCKS_WIDE / 2;
        snakeY[0] = numBlocksHeight / 2;

        score = 0;

        spawnFood();

        nextFrame = System.currentTimeMillis();
    }

    private void spawnFood(){
        Random random = new Random();

        foodX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        foodY = random.nextInt(numBlocksHeight - 1 ) + 1;
    }

    private boolean updateRequired(){
        if (nextFrame <= System.currentTimeMillis()){
            nextFrame = System.currentTimeMillis() +
                    MILLIS_PER_SECOND / FPS;

            return true;
        }

        return false;
    }

    private void update(){

        if (snakeX[0] == foodX && snakeY[0] == foodY){
            eatFruit();
        }

        moveSnake();

        if (detectDeath()){
            // Update the global score

            // Adds an increment to the achievement

            newGame();
        }
    }

    private boolean detectDeath(){

        boolean dead = false;

        // Testar as bordas
        if (snakeX[0] == -1) dead = true;
        if (snakeX[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snakeY[0] == -1) dead = true;
        if (snakeY[0] >= numBlocksHeight) dead = true;

        for (int i = snakeLenght - 1; i > 0; i--){

            if ((i > 4) && (snakeX[0] == snakeX[i]) &&
                    (snakeY[0] == snakeY[i]))
                dead = true;
        }

        return dead;
    }

    private void eatFruit(){
        snakeLenght++;

        spawnFood();

        score += 5;

        // Check the score for unlocking achievements
    }

    private void draw(){

        if (surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(
                    255, 26, 128, 182));

            paint.setColor(Color.WHITE);

            paint.setTextSize(90);
            canvas.drawText("Score: " + score, 10, 70, paint);

            for (int i = 0; i < snakeLenght; i++){
                canvas.drawRect(snakeX[i] * blockSize,
                        snakeY[i] * blockSize,
                        (snakeX[i] * blockSize) + blockSize,
                        (snakeY[i] * blockSize) + blockSize,
                        paint);
            }

            paint.setColor(Color.argb(
                    255, 255, 0, 0));

            canvas.drawRect(foodX * blockSize,
                    foodY * blockSize,
                    foodX * blockSize + blockSize,
                    foodY * blockSize + blockSize,
                    paint);

            surfaceHolder.unlockCanvasAndPost(canvas);


        }

    }

    private void moveSnake(){

        for (int i = snakeLenght; i > 0; i--){
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (heading){
            case UP:
                snakeY[0]--;
                break;

            case DOWN:
                snakeY[0]++;
                break;

            case LEFT:
                snakeX[0]--;
                break;

            case RIGHT:
                snakeX[0]++;
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_UP:
                if (event.getX() >= size.x / 2) {
                    switch (heading){

                        case UP:
                            heading = Heading.RIGHT;
                            break;

                        case RIGHT:
                            heading = Heading.DOWN;
                            break;

                        case DOWN:
                            heading = Heading.LEFT;
                            break;

                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch (heading) {

                        case UP:
                            heading = Heading.LEFT;
                            break;

                        case RIGHT:
                            heading = Heading.UP;
                            break;

                        case DOWN:
                            heading = Heading.RIGHT;
                            break;

                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                    }

                }
                break;
        }

        return true;
    }
}
