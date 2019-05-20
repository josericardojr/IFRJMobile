package com.josericardojr.snakeapp;

import android.app.Activity;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;

public class MainActivity extends Activity {

    Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        setContentView(R.layout.activity_main);
        engine = new Engine(this, size);

        // Add the engine to the ConstraintLayout instead of setting it as the main view.
        // Its because we are going to use a view component for showing popups when the user
        // unlocks a new achievements
        ConstraintLayout cl = findViewById(R.id.layoutCL);
        cl.addView(engine);


        // Set a view to show a popup when the user unlocks an achievement
        GamesClient gc = Games.getGamesClient(MainActivity.this, GoogleSignIn.getLastSignedInAccount(this));
        gc.setViewForPopups(findViewById(R.id.gps_popup));
    }

    @Override
    protected void onResume() {
        super.onResume();

        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        engine.pause();
    }
}
