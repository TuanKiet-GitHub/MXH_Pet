package com.example.pet.mode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pet.R;

public class MainPage extends AppCompatActivity {
    TextView txtLogin ;
    TextView tvRegistration ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        anhXa();
        event();
    }
    void event ()
    {
        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this , RegistrationActivity.class);
                startActivity(intent);
                finish();

            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    void anhXa()
    {
        txtLogin = findViewById(R.id.txtDangNhap);
        tvRegistration = findViewById(R.id.tvDangKi);
    }
}