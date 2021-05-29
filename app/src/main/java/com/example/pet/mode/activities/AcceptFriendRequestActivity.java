package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pet.R;
import com.example.pet.databinding.ActivityAcceptFriendRequestBinding;
import com.example.pet.mode.adapters.AcceptFriendRequestAdapter;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AcceptFriendRequestActivity extends AppCompatActivity {
    ActivityAcceptFriendRequestBinding binding;
    AcceptFriendRequestAdapter adapter;
    ArrayList<User> users;
    DatabaseReference reference;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AcceptFriendRequestActivity.this, R.layout.activity_accept_friend_request);
        users = new ArrayList<>();
        adapter = new AcceptFriendRequestAdapter(AcceptFriendRequestActivity.this, users);
        setListFriendRequest();

        binding.btnBack.setOnClickListener(v -> finish());

        token = Utils.getToken(AcceptFriendRequestActivity.this);
        try {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(token).child("list_request_friend");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() == 0){
                        binding.text.setText("You don't have any friend request.");
                        binding.text.setVisibility(View.VISIBLE);
                    }else binding.text.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }catch (Exception ignored){}

    }

    private void setListFriendRequest() {
        String token = Utils.getToken(AcceptFriendRequestActivity.this);
        try {
            DatabaseReference reference =
                    FirebaseDatabase.getInstance().getReference("Users").
                            child(token).child("list_request_friend");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    users.clear();
                    for (DataSnapshot s : snapshot.getChildren()) {
                        String temp = s.getKey();
                        Log.e("TAG", "onDataChange: " + temp);
                        assert temp != null;
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(temp);
                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    User user = snapshot.getValue(User.class);
                                    users.add(user);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }

                    binding.rcvList.setLayoutManager(new LinearLayoutManager(AcceptFriendRequestActivity.this));
                    binding.rcvList.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception ignored) {
        }
    }

}