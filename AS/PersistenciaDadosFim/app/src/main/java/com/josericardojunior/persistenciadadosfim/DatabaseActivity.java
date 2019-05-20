package com.josericardojunior.persistenciadadosfim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DatabaseActivity extends AppCompatActivity {

    Button btnInsert, btnQuery;
    EditText edtNick, edtScore, edtId;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sqlite_activity);

        btnInsert = findViewById(R.id.btnSQLInsert);
        btnQuery = findViewById(R.id.btnSQLQuery);
        edtNick = findViewById(R.id.edtNickname);
        edtScore = findViewById(R.id.edtScore);
        edtId = findViewById(R.id.txtID);
        dbHelper = new DBHelper(this);
    }

    public void buttonClick(View view) {

        if (view == btnInsert) {
            GameScore gs = new GameScore();
            gs.nickname = edtNick.getText().toString();
            gs.score = Integer.parseInt(edtScore.getText().toString());
            dbHelper.addGameScore(gs);

            edtNick.setText("");
            edtScore.setText("0");
        } else if (view == btnQuery) {

            int _id = Integer.parseInt(
                    edtId.getText().toString());

            GameScore gs = dbHelper.retrieveGS(_id);

            if (gs != null) {
                edtNick.setText(gs.nickname);
                edtScore.setText(String.valueOf(gs.score));
            } else {
                edtNick.setText("");
                edtScore.setText("0");
            }

        }
    }
}
