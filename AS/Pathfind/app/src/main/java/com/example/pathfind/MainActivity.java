package com.example.pathfind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    Scene1 scene1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_main);
        scene1 = findViewById(R.id.scene1);

        ((RadioGroup)findViewById(R.id.radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rbStart:
                        scene1.setCurrent_mode(Scene1.MODE_START);
                        break;

                    case R.id.rbEnd:
                        scene1.setCurrent_mode(Scene1.MODE_END);
                        break;

                    case R.id.rbObstacle:
                        scene1.setCurrent_mode(Scene1.MODE_BLOCK);
                        break;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        scene1.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        scene1.stop();
    }

    public void onClick(View view){
        if (view == findViewById(R.id.btnClear)){
            scene1.clearPath();
        } else if (view == findViewById(R.id.btnMakePath)){
            scene1.makePath();
        }
    }
}