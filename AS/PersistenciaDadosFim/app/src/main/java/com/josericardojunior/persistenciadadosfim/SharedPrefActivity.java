package com.josericardojunior.persistenciadadosfim;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.josericardojunior.persistenciadadosfim.R;

public class SharedPrefActivity extends AppCompatActivity {

    EditText edtNick;
    EditText edtScore;
    Button btnSave, btnRetrieve;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shared_pref);

        edtNick = (EditText) findViewById(R.id.edtNick);
        edtScore = (EditText) findViewById(R.id.txtScore);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnRetrieve = (Button) findViewById(R.id.btnRetrieve);
    }

    public void ButtonClickShared(View view){

        if (view == btnSave){
            SharedPreferences sharedPreferences = getSharedPreferences(
                    "AppConfig", MODE_PRIVATE);

            SharedPreferences.Editor edt = sharedPreferences.edit();
            edt.putString("Nickname", edtNick.getText().toString());
            edt.putInt("Score", Integer.parseInt(
                    edtScore.getText().toString()));
            edt.commit();
        } else if (view == btnRetrieve){
            SharedPreferences sharedPreferences = getSharedPreferences(
                    "AppConfig", MODE_PRIVATE);

            edtNick.setText(sharedPreferences.getString("Nickname", "Nenhum"));
            edtScore.setText(
                    String.valueOf(
                            sharedPreferences.getInt("Score", 0)));
        }
    }
}
