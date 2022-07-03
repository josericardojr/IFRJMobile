package com.example.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class GamePlayActivity extends AppCompatActivity implements View.OnClickListener {

    boolean is_myturn = false;
    String mark = "O";
    String host = "";
    Map<String,Button> whereToPlay = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        whereToPlay.put( "btn11",
                (Button)findViewById(R.id.btn11));
        whereToPlay.put( "btn12",
                (Button)findViewById(R.id.btn12));
        whereToPlay.put("btn13",
                (Button)findViewById(R.id.btn13));
        whereToPlay.put("btn21",
                (Button)findViewById(R.id.btn21));
        whereToPlay.put( "btn22",
                (Button)findViewById(R.id.btn22));
        whereToPlay.put( "btn23",
                (Button)findViewById(R.id.btn23));
        whereToPlay.put( "btn31",
                (Button)findViewById(R.id.btn31));
        whereToPlay.put( "btn32",
                (Button)findViewById(R.id.btn32));
        whereToPlay.put( "btn33",
                (Button)findViewById(R.id.btn33));

        for (Map.Entry<String,Button> entry : whereToPlay.entrySet()) {
            entry.getValue().setText("");
            entry.getValue().setOnClickListener(this);
        }

        host = getIntent().getExtras().getString("host");
        setFirebaseHandler(host);
    }

    private void setFirebaseHandler(String host) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //@TODO handle the notification when a new slot is selected

        if (uid.equals(host)) {
            is_myturn = true;
            mark = "X";
        }
    }

    private void checkVictory() {
    }

    @Override
    public void onClick(View view) {
        if (!is_myturn)
            return;

        Button currentBtn = (Button) view;
        if (!currentBtn.getText().toString().isEmpty())
            return;

        String btName = getResources().getResourceName(currentBtn.getId());
        btName = btName.substring(btName.indexOf('b'));

        //@TODO save on firebase
    }
}
