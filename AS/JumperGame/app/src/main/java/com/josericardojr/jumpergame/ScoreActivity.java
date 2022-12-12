package com.josericardojr.jumpergame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScoreActivity extends AppCompatActivity {

    TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txtScore = findViewById(R.id.txtScore);
    }

    @Override
    protected void onStart() {
        super.onStart();

        int score = 0;

        try {
            FileInputStream fis = openFileInput("score.txt");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis));

            String line = br.readLine();
            score = Integer.parseInt(line);

            while ( line != null){
                score = Integer.parseInt(line);
                line = br.readLine();
            }

            br.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        txtScore.setText(String.valueOf(score));
    }
}