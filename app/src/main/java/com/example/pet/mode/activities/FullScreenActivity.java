package com.example.pet.mode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.ActivityFullScreenBinding;
import com.example.pet.mode.models.Image;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FullScreenActivity extends AppCompatActivity {
    ActivityFullScreenBinding fullScreenBinding;
    ArrayList<Image> images;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fullScreenBinding = DataBindingUtil.setContentView(FullScreenActivity.this, R.layout.activity_full_screen);

        getListImage();
        setImages(current);

        fullScreenBinding.back.setOnClickListener(v -> finish());

        fullScreenBinding.next.setOnClickListener(v -> {
            current++;
            if (current >= images.size()) {
                current = 0;
            }
        
            setImages(current);
        });

        fullScreenBinding.previous.setOnClickListener(v -> {
            current--;
            if (current < 0) {
                current = 0;
            }
            setImages(current);
        });

        fullScreenBinding.image.setOnClickListener(v -> {
            current++;
            if (current >= images.size()) {
                current = 0;
            }

            setImages(current);
        });
    }

    public void setImages(int position) {
        Glide.with(getApplicationContext())
                .load(images.get(position).getImage())
                .into(fullScreenBinding.image);
    }

    private void getListImage() {
        images = new ArrayList<>();

        Intent intent = getIntent();
        String temp = intent.getStringExtra("list_image");
        current = intent.getIntExtra("position", 0);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Image>>() {
        }.getType();
        images = gson.fromJson(temp, collectionType);
    }
}