package com.example.pet.mode.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pet.R;
import com.example.pet.mode.DKDN.Home;

public class StatusActivity extends AppCompatActivity {
    EditText edStatus ;
    Button btnLoadAnh, btnTag , btnDangBai;
    ImageView imgBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        getSupportActionBar().hide();
        Anhxa();
        Event();
    }
    void Event ()
    {
        //  Nhấn vào mất dùng chữ hôm nay bạn nghĩ gì.
        edStatus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edStatus.setText("");
                btnDangBai.setEnabled(true);
                btnDangBai.setBackgroundColor(getApplication().getResources().getColor(R.color.yellowmain2));
            }
        });
        imgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });


    }
    void Anhxa()
    {
        imgBackHome = findViewById(R.id.imgBackStatus);
        btnDangBai = findViewById(R.id.btnDangBai);
        btnLoadAnh = findViewById(R.id.btnLoadAnh);
        btnTag = findViewById(R.id.btnTag);
        edStatus = findViewById(R.id.edStatus);

    }

}