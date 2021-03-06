package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.mode.home.LoveFragment;
import com.example.pet.mode.home.MessageFragment;
import com.example.pet.mode.home.ProfileFragment;
import com.example.pet.mode.home.MainPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    Button btnSquare, btnLove, btnMessage, btnProfile;
    LinearLayout linerSquare, linearLove, linerMessage, linerProfile, linear;
    Fragment fragment;
    TextView notification;
    ImageButton btn_search_friend;

    private DatabaseReference reference;
    private String token;
    public static String status;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anhxa();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "1");
        // Mới vào thì home Fragment xuất hiện đầu tiên
        fragment = new MainPageFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                fragment).commit();
        btnSquare.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.vuong01), null, null);
        linerSquare.setBackgroundColor(getApplication().getResources().getColor(R.color.yello));
        Event();

        linear.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AcceptFriendRequestActivity.class);
            startActivity(intent);
        });

        try {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(token).child("list_request_friend");
            reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0 ){
                    notification.setVisibility(View.VISIBLE);
                    notification.setText(snapshot.getChildrenCount() +"");
                }else notification.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        }catch (Exception ignored){}

    }

    // region ANH XA
    void anhxa() {
        btnProfile = findViewById(R.id.btnProfile);
        btnLove = findViewById(R.id.btnLove);
        btnMessage = findViewById(R.id.btnMessage);
        btnSquare = findViewById(R.id.btnSquare);
        linerSquare = findViewById(R.id.linerSquare);
        linearLove = findViewById(R.id.linerLove);
        linerMessage = findViewById(R.id.linerMessage);
        linerProfile = findViewById(R.id.linerProfile);
        btn_search_friend = findViewById(R.id.btn_search_friend);
        linear = findViewById(R.id.linear);
        notification = findViewById(R.id.request_friend);
    }
    // endregion

    // region  SỰ KIỆN CHUYỂN FRAMENT
    @SuppressLint("UseCompatLoadingForDrawables")
    void Event() {

        btnSquare.setOnClickListener(v -> {
            btnSquare.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.vuong01), null, null);
            linerSquare.setBackgroundColor(getApplication().getResources().getColor(R.color.yello));
            linerMessage.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linerProfile.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linearLove.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            btnMessage.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.message2), null, null);
            btnLove.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tim), null, null);
            btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.nguoi), null, null);

            fragment = new MainPageFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    fragment).commit();
        });
        btnLove.setOnClickListener(v -> {
            btnLove.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tim01), null, null);
            linearLove.setBackgroundColor(getApplication().getResources().getColor(R.color.yello));
            linerSquare.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linerMessage.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linerProfile.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            btnMessage.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.message2), null, null);
            btnSquare.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.vuong), null, null);
            btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.nguoi), null, null);
            fragment = new LoveFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    fragment).commit();
        });
        btnMessage.setOnClickListener(v -> {
            btnMessage.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.message1), null, null);
            linerMessage.setBackgroundColor(getApplication().getResources().getColor(R.color.yello));
            linearLove.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linerSquare.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linerProfile.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            btnSquare.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.vuong), null, null);
            btnLove.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tim), null, null);
            btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.nguoi), null, null);
            fragment = new MessageFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    fragment).commit();
        });
        btnProfile.setOnClickListener(v -> {
            btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.nguoi01), null, null);
            linerProfile.setBackgroundColor(getApplication().getResources().getColor(R.color.yello));
            linearLove.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linerSquare.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            linerMessage.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
            btnMessage.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.message2), null, null);
            btnSquare.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.vuong), null, null);
            btnLove.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tim), null, null);
            fragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    fragment).commit();
        });
        btn_search_friend.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, SearchFriendActivity.class));
//                Toast.makeText(HomeActivity.this, "Test", Toast.LENGTH_SHORT).show();
//                Log.e("C", "Click");
        });


    }

    private void status(String status) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

    // endregion
}