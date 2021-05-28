package com.example.pet.mode.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.activities.SearchFriendActivity;
import com.example.pet.mode.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFriendFragment extends Fragment {
    private DatabaseReference databaseReference;
    CircleImageView circleImageView;
    TextView userName, nickName, gender, DOB, address, phone;
    Button send, decline;
    ImageButton back;
    String id_friend;
    SharedPreferences sharedPreferences;
    String token;
    String currentState = "nothing_happen";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_friend, container, false);

        id_friend = this.getArguments().getString("id");
        sharedPreferences = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "1");

        mapped(view);

        Log.e("TAG", "" + id_friend);

        loadUser();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new ProfileFriendFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_search_friend, myFragment).addToBackStack(null).commit();

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAction();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void mapped(View view) {
        circleImageView = view.findViewById(R.id.avatar_friend);
        userName = view.findViewById(R.id.tv_username_friend);
        nickName = view.findViewById(R.id.tv_nick_name_friend);
        gender = view.findViewById(R.id.tv_gender_friend);
        DOB = view.findViewById(R.id.tv_year_friend);
        address = view.findViewById(R.id.tv_address_friend);
        phone = view.findViewById(R.id.tv_phoneNumber_friend);

        send = view.findViewById(R.id.SendRequest_friend);
        decline = view.findViewById(R.id.DeclineRequest_friend);
        back = view.findViewById(R.id.btn_back);
    }

    private void loadUser() {
        if (!id_friend.isEmpty()) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id_friend);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        User user = snapshot.getValue(User.class);
                        Glide.with(getContext()).load(user.getAvatar()).into(circleImageView);
                        userName.setText(user.getFull_name());
                        nickName.setText(user.getNick_name());
                        gender.setText("Gender: " + user.getGender());
                        DOB.setText("Day Of Birth: " + user.getYear_born());
                        address.setText("Address: " + user.getAddress());
                        phone.setText("Phone Number: " + user.getPhone_number());
//                            Log.e("TAG", "" + user);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    private void performAction() {


    }
}
