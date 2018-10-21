package com.josericardojunior.persistenciadadosinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.josericardojunior.persistenciadados.R;

public class MainActivity extends AppCompatActivity {

    Button btnSharedPref;
    Button btnFile;
    Button btnSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSharedPref = (Button) findViewById(R.id.idSharedPref);
        btnFile = (Button) findViewById(R.id.btnFileSelect);
        btnSQL = (Button) findViewById(R.id.btnSQL);
    }

    public void ButtonClick(View view){
        if (view == btnSharedPref){

        } else if (view == btnFile){

        } else if (view == btnSQL){

        }
    }
}
