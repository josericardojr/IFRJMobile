package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import com.example.tictactoe.databinding.ActivityUsersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    public static final String CON_ACCEPTED = "ACC";
    public static final String CON_REFUSED = "REF";

    private Adapter adapter;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUsersBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_users);

        adapter = new com.example.tictactoe.Adapter(users);
        binding.list.setAdapter(adapter);
        binding.list.setLayoutManager(new LinearLayoutManager(this));

        //@TODO: in session register

        setListener();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //@TODO: in session remove
    }

    @Override
    protected void onResume() {
        super.onResume();

        queryUsers();
    }

    private void setListener(){
        //@TODO: check invitations
    }

    private void handleInvitation(GameSession gameSession) {

        if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Game Invitation. Accept?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gameSession.accepted = CON_ACCEPTED;

                            //@TODO: signal the host

                            Intent intent = new Intent(UsersActivity.this,
                                    GamePlayActivity.class);
                            intent.putExtra("host", gameSession.host);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gameSession.accepted = CON_REFUSED;

                            //@TODO: signal the host about invitation refused
                        }
                    });

            builder.create().show();
        }
    }

    private void queryUsers() {

        //@TODO: handle users actually online
    }
}