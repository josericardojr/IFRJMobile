package com.josericardojr.snakeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainMenu extends Activity {

    Button btnPlay, btnAchievements, btnLogIn, btnLogOut, btnLeaderboard;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_SIGN_IN = 9004;
    private static final int RC_LEADERBOARD = 9005;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);

        btnPlay = findViewById(R.id.btnPlay);
        btnAchievements = findViewById(R.id.btnAchievement);
        btnLogIn = findViewById(R.id.btnLogin);
        btnLogOut = findViewById(R.id.btnLogout);
        btnLeaderboard = findViewById(R.id.btnLeaderboard);

        googleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        signInSilently();
    }

    public void onClick(View view){

        if (btnAchievements == view){
            Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .getAchievementsIntent()
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                        }
                    });

        } else if (btnLogOut == view){
            signOut();
        } else if (btnLogIn == view){
            startSignInIntent();
        } else if (btnLeaderboard == view){
            Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .getLeaderboardIntent(getString(R.string.leaderboard_easy_high_score))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADERBOARD);
                        }
                    });
        } else if (btnPlay == view){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void signInSilently() {


        googleSignInClient
                .silentSignIn()
                .addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            GoogleSignInAccount signedInAccout = task.getResult();
                            Log.d("LOG", "signInSilently successful");
                        } else {
                            Log.d("LOG", "signInSilently failure");
                            startSignInIntent();
                        }
                    }
                });

    }

    private void startSignInIntent(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){
                GoogleSignInAccount signedInAccout = result.getSignInAccount();
            } else {
                String message = result.getStatus().getStatusMessage();

                if (message == null || message.isEmpty())
                    message = "Outro erro!";

                new AlertDialog.Builder(this).setMessage(message).setNeutralButton(android.R.string.ok, null).show();
            }


        }
    }

    private void signOut(){
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}
