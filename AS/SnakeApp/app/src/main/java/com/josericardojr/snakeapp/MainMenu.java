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
    GoogleSignInClient googleSignInClient;

    private static final int RC_LOGIN = 9004;
    private static final int RC_ACHIEVEMENTS = 9005;
    private static final int RC_LEADBOARD = 9006;

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
                new GoogleSignInOptions.Builder(
                        GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        signinSilent();
    }

    public void onClick(View view){

        if (btnAchievements == view){
            // Show the achievements
            Games.getAchievementsClient(this,
                    GoogleSignIn.getLastSignedInAccount(this))
                    .getAchievementsIntent()
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_ACHIEVEMENTS );
                        }
                    });

        } else if (btnLogOut == view){
            // Logout the user
            signOut();
        } else if (btnLogIn == view){
            startSigninIntent();
        } else if (btnLeaderboard == view){
            // Show Leaderboard
            Games.getLeaderboardsClient(this,
                    GoogleSignIn.getLastSignedInAccount(this))
                    .getLeaderboardIntent(
                            getString(R.string.leaderboard_high_score))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADBOARD );
                        }
                    });
        } else if (btnPlay == view){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void signinSilent(){

        googleSignInClient
                .silentSignIn()
                .addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {

                        if (task.isSuccessful()){
                            GoogleSignInAccount signInAccount =
                                    task.getResult();
                            Log.d("LOGIN", "silentSignIn() sucessfully");
                        } else {
                            Log.d("LOGIN", "silentSignIn() failure");
                            startSigninIntent();
                        }
                    }
                });
    }

    private void startSigninIntent(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_LOGIN){

            GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){
                GoogleSignInAccount signInAccount =
                        result.getSignInAccount();
            } else {
                String message = result.getStatus().getStatusMessage();

                if (message != null || message.isEmpty()){
                    message = "Outro erro!";
                }

                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
            }
        } else if (requestCode == RC_ACHIEVEMENTS){

        } else if (requestCode == RC_LEADBOARD){

        }
    }

    private void signOut(){
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
