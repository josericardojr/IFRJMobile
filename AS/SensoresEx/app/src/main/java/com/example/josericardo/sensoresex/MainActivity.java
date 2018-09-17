package com.example.josericardo.sensoresex;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;
    SensorManager sensorManager;
    List<Sensor> sensors;
    Sensor accelSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        setContentView(drawView);

        sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        accelSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        for (Sensor s : sensors){
            Log.d("Sensor", s.getName());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        drawView.stop();
        sensorManager.unregisterListener(drawView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawView.resume();
        sensorManager.registerListener(drawView,
                accelSensor, 5);
    }

}
