package com.josericardojr.jumpergame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    GameLoop gameLoop;
    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        gameLoop = new GameLoop(this, metrics.widthPixels, metrics.heightPixels);
        gameLoop.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(gameLoop);

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);
        backgroundMusic.setVolume(0.2f, 0.2f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundMusic.start();
        gameLoop.startNewGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundMusic.stop();
    }
}