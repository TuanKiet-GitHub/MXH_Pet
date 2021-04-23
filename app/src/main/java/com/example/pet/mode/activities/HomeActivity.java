package com.example.pet.mode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.pet.R;
import com.example.pet.mode.home.LoveFragment;
import com.example.pet.mode.home.MessageFragment;
import com.example.pet.mode.home.ProfileFragment;
import com.example.pet.mode.home.SquareFragment;

public class HomeActivity extends AppCompatActivity {
    Button btnSquare , btnLove , btnMessage , btnProfile , btnHome;
    LinearLayout linerSquare , linearLove , linerMessage , linerProfile ;
    Fragment fragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anhxa();
        // Mới vào thì home Fragment xuất hiện đầu tiên
        fragment = new SquareFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                fragment).commit();
        btnSquare.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.vuong01), null, null);
        linerSquare.setBackgroundColor(getApplication().getResources().getColor(R.color.yello));
        Event();

    }

    // region ANH XA
    void anhxa()
    {
        btnProfile = findViewById(R.id.btnProfile);
        btnLove = findViewById(R.id.btnLove);
        btnMessage = findViewById(R.id.btnMessage);
        btnSquare = findViewById(R.id.btnSquare);
        linerSquare = findViewById(R.id.linerSquare);
        linearLove = findViewById(R.id.linerLove);
        linerMessage = findViewById(R.id.linerMessage);
        linerProfile = findViewById(R.id.linerProfile);
    }
    // endregion

    // region  SỰ KIỆN CHUYỂN FRAMENT
    void Event()
    {

        btnSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSquare.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.vuong01), null, null);
                linerSquare.setBackgroundColor(getApplication().getResources().getColor(R.color.yello));
                linerMessage.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
                linerProfile.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
                linearLove.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
                btnMessage.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.message2), null, null);
                btnLove.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tim), null, null);
                btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.nguoi), null, null);

                fragment = new SquareFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                        fragment).commit();
            }
        });
        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
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
            }
        });




    }

    // endregion
}