package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tictactoe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "TIC_TAC_TOE";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }


    private boolean areServicesOK() {
        //@TODO
        return false;
    }

    public void startMultiPlayer(View view) {
        if (!areServicesOK())
            return;

        if (isAnonymous()) {
            binding.txtName.setVisibility(View.VISIBLE);
            binding.txtEmail.setVisibility(View.VISIBLE);
            binding.inputPassword.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.VISIBLE);
        } else {
            startActivity(new Intent(this, UsersActivity.class));
        }
    }

    public void loginWithEmail(View view) {
        String email = binding.txtEmail.getText().toString();
        String name = binding.txtName.getText().toString();
        String pass = binding.inputPassword.getText().toString();

        //@TODO
    }

    private boolean isAnonymous() {
        //@TODO
        return false;
    }
}