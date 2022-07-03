package com.example.tictactoe;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoe.databinding.UserListItemBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class Holder extends RecyclerView.ViewHolder {
    UserListItemBinding binding;

    public Holder(@NonNull View itemView) {
        super(itemView);

        binding = DataBindingUtil.bind(itemView);
        binding.btnInvite.setOnClickListener(view -> {

            //@TODO: send invitation
        });
    }
}
