package com.josericardojr.jumpergame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameLoop extends View implements Runnable, View.OnTouchListener {
    private static final int SND_COLLISION_ID = 0;
    private static final int SND_POINTS_ID = 1;
    private static final int SND_JUMP_ID = 2;
    public static final int PIPE_DISTANCE = 300;
    public static final int FRAME_MS = 1000 / 30;
    public static final int NUM_PIPES = 7;
    Passaro passaro = new Passaro();
    Pipe []pipes = new Pipe[NUM_PIPES];
    int pontuacao = 0;
    Handler handler;
    int screen_w, screen_h;
    public boolean isGameOver = false;
    SoundPool gameEffects;
    int [] effectsId = new int[3];

    public GameLoop(Context context, int s_width, int s_height) {
        super(context);
        screen_w = s_width;
        screen_h = s_height;
        setOnTouchListener(this);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        gameEffects = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        effectsId[SND_COLLISION_ID] = gameEffects.load(getContext(), R.raw.colisao, 1);
        effectsId[SND_POINTS_ID] = gameEffects.load(getContext(), R.raw.pontos, 1);
        effectsId[SND_JUMP_ID] = gameEffects.load(getContext(), R.raw.pulo, 1);
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
                    gameEffects.play(effectsId[SND_COLLISION_ID],
                            1, 1, 1, 0, 1);
                    saveScore();
                    isGameOver = true;
                    break;
                }

                if (pipes[i].getPositionX() + Pipe.PIPE_WIDTH < 0) {
                    gameEffects.play(effectsId[SND_POINTS_ID],
                            1, 1, 1, 0, 1);
                    int posXLastPipe = getPositionXLastPipe();
                    pipes[i].setPositionX(posXLastPipe + Pipe.PIPE_WIDTH + PIPE_DISTANCE);
                    pipes[i].respawn();
                    pontuacao += 5;
                }
            }
        }
    }

    private void saveScore() {
        try {
            FileOutputStream fos = getContext().openFileOutput("score.txt", Context.MODE_APPEND);
            String strScore = String.valueOf(pontuacao).concat("\n");
            fos.write (strScore.getBytes());
            fos.close();
        } catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
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
            if (!isGameOver){
                gameEffects.play(effectsId[SND_JUMP_ID],
                        1, 1, 1, 0, 1);
                passaro.pula();
            }
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
