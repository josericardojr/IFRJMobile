package com.josericardojr.snakeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends Activity {

    Button btnPlay, btnAchievements, btnLogIn, btnLogOut, btnLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);

        btnPlay = findViewById(R.id.btnPlay);
        btnAchievements = findViewById(R.id.btnAchievement);
        btnLogIn = findViewById(R.id.btnLogin);
        btnLogOut = findViewById(R.id.btnLogout);
        btnLeaderboard = findViewById(R.id.btnLeaderboard);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View view){

        if (btnAchievements == view){
            // Show the achievements

        } else if (btnLogOut == view){
            // Logout the user
        } else if (btnLogIn == view){
            // Login the user
        } else if (btnLeaderboard == view){
            // Show Leaderboard
        } else if (btnPlay == view){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
