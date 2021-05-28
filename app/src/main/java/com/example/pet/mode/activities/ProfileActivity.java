package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.ActivityProfileBinding;
import com.example.pet.mode.home.ProfileFragment;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity {
    private String id_friend;
    private SharedPreferences sharedPreferences;
    private ActivityProfileBinding binding;
    private DatabaseReference databaseReference;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ProfileActivity.this, R.layout.activity_profile);

        token = Utils.getToken(ProfileActivity.this);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        id_friend = getIntent().getStringExtra("id_friend");

        getProfileUser();


        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSendRequestMakeFriend.setOnClickListener(v -> {
            sendRequestMakeFriend();
        });

    }

    private void sendRequestMakeFriend() {

        String temp = binding.btnSendRequestMakeFriend.getText().toString();
        if (temp.equals("Request Make Friend")) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(token);
            databaseReference.child("list_request_friend").child(id_friend).setValue("true");
            Toast.makeText(this, "Sent a request make friend.", Toast.LENGTH_SHORT).show();
            setButton("Cancelling friend request", Color.WHITE, Color.GRAY);
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(token);
            databaseReference.child("list_request_friend").child(id_friend).removeValue();
            Toast.makeText(this, "Cancelling friend request", Toast.LENGTH_SHORT).show();
            setButton(getResources().getString(R.string.request_make_friend), Color.BLACK, getResources().getColor(R.color.colorButton));
        }


    }

    private void getProfileUser() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id_friend);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                binding.setUser(user);
                binding.tvAddress.setText("Address: " + user.getAddress());
                binding.tvGender.setText("Gender: " + user.getGender());
                binding.tvPhoneNumber.setText("Phone Number: " + user.getPhone_number());
                binding.tvYear.setText("Day of Birth: " + user.getYear_born());
                if (!user.getAvatar().equals("default") && getApplicationContext() != null) {
                    Glide.with(ProfileActivity.this).load(Uri.parse(user.getAvatar()))
                            .into(binding.avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(token).child("list_request_friend");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot s : snapshot.getChildren()) {

                            if (s.getKey().equals(id_friend)) {
                                setButton("Cancelling friend request", Color.WHITE, Color.GRAY);
                            }
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }

    }

    private void setButton(String text, int textColor, int buttonColor) {
        binding.btnSendRequestMakeFriend.setText(text);
        binding.btnSendRequestMakeFriend.setBackgroundColor(buttonColor);
        binding.btnSendRequestMakeFriend.setTextColor(textColor);
    }
}