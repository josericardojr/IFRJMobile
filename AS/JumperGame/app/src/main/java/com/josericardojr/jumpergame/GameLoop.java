package com.josericardojr.jumpergame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends View implements Runnable, View.OnTouchListener {
    public static final int PIPE_DISTANCE = 300;
    public static final int FRAME_MS = 1000 / 30;
    public static final int NUM_PIPES = 7;
    Passaro passaro = new Passaro();
    Pipe []pipes = new Pipe[NUM_PIPES];
    int pontuacao = 0;
    Handler handler;
    int screen_w, screen_h;
    public boolean isGameOver = false;

    public GameLoop(Context context, int s_width, int s_height) {
        super(context);
        screen_w = s_width;
        screen_h = s_height;
        setOnTouchListener(this);
    }


    public void startNewGame(){
        pontuacao = 0;
        isGameOver = false;
        passaro.setPosition(100, (int)(screen_h / 2.0f));

        for (int i = 0; i < NUM_PIPES; i++){
            pipes[i] = new Pipe(screen_w, screen_h);
            pipes[i].setPositionX(screen_w + (Pipe.PIPE_WIDTH + PIPE_DISTANCE) * i);
            pipes[i].respawn();
        }

        handler = new Handler();
        handler.postDelayed(this, FRAME_MS);
    }

    private void update(){
        if (!isGameOver) {
            passaro.update();

            for (int i = 0; i < NUM_PIPES; i++) {
                pipes[i].update();

                // Check Collision
                if (pipes[i].checkCollision(passaro.getBoundingVolume())){
                    isGameOver = true;
                    break;
                }

                if (pipes[i].getPositionX() + Pipe.PIPE_WIDTH < 0) {
                    int posXLastPipe = getPositionXLastPipe();
                    pipes[i].setPositionX(posXLastPipe + Pipe.PIPE_WIDTH + PIPE_DISTANCE);
                    pipes[i].respawn();
                    pontuacao += 5;
                }
            }
        }
    }

    private int getPositionXLastPipe() {
        int posXLastPipe = -1;

        for (int i = 0; i < NUM_PIPES; i++){
            if (pipes[i].getPositionX() > posXLastPipe)
                posXLastPipe = pipes[i].getPositionX();
        }

        return posXLastPipe;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        passaro.draw(canvas);

        for (Pipe p : pipes)
            p.draw(canvas);

        exibirPontuacao(canvas);

        if (isGameOver){
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setTextSize(50);
            p.setTypeface(Typeface.DEFAULT_BOLD);

            Rect limiteTexto = new Rect();
            String gameOver = "Game Over";
            p.getTextBounds(gameOver, 0, gameOver.length(), limiteTexto );

            canvas.drawText(gameOver, screen_w / 2 - limiteTexto.centerX(),
                    screen_h / 2 - limiteTexto.centerY(), p);
        }
    }

    @Override
    public void run() {

        if (!isGameOver) {
            update();
            invalidate();

            handler.postDelayed(this, FRAME_MS);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
            if (!isGameOver) passaro.pula();
            else startNewGame();

            return true;
        }

        return false;
    }

    private void exibirPontuacao(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(80);
        p.setTypeface(Typeface.DEFAULT_BOLD);
        p.setShadowLayer(3, 5, 5, Color.BLACK);

        canvas.drawText("Pontos: " + String.valueOf(pontuacao),
                100, 100, p);
    }
}
