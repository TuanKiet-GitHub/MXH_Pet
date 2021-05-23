package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pet.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;



public class UpdatePassActivity extends AppCompatActivity {
    ImageButton imgButton ;
    Button btnUpdatePassword ;
    EditText edCurrentPass , edNewPass, edConfirmPass;
    ImageView imgCurrentPass, imgNewPass, imgConfirmPass;
    Boolean checkCurrennt = false, checkNewPass = false;
    private SharedPreferences sharedPreferences;
    private String savePassWord ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        imgButton = findViewById(R.id.viewBack);
        btnUpdatePassword = findViewById(R.id.updatePassWord);
        edCurrentPass = findViewById(R.id.edCurrentPass);
        edNewPass = findViewById(R.id.edNewPass);
        edConfirmPass = findViewById(R.id.edConfirmNewPass);
        imgNewPass = findViewById(R.id.imgNewPass);
        imgConfirmPass = findViewById(R.id.imgConfirmNewPass);
        imgCurrentPass = findViewById(R.id.imgCurrentPass);
        sharedPreferences = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        savePassWord = sharedPreferences.getString("password", "1");
        Log.e("Click", "Pass " + savePassWord);
        eventClick();

    }
    private void eventClick()
    {
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassWord = edConfirmPass.getText().toString().trim();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updatePassword(newPassWord).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sharedPreferences = getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password", newPassWord);
                        editor.apply();
                     //   getFragmentManager().beginTransaction().add(R.id.fragmentUpdatePass , new SuccessFragment()).commit();
                        Toast.makeText(getApplicationContext(), "UPDATE PASSWORD SUCCESS !!! ", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(() -> {
                            finish();
                        },1000);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "UPDATE PASSWORD FAIL !!! ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        edCurrentPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (edCurrentPass.getText().toString().trim().equals(savePassWord))
                {
                    imgCurrentPass.setVisibility(View.VISIBLE);
                    checkCurrennt = true ;
                }
                else
                {
                    imgCurrentPass.setVisibility(View.INVISIBLE);
                }
            }
        });
        edNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edNewPass.getText().length() > 8)
                {
                    imgNewPass.setVisibility(View.VISIBLE);
                    checkNewPass =true ;
                }
                else
                {
                    imgNewPass.setVisibility(View.INVISIBLE);
                }
            }
        });
        edConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edNewPass.getText().toString().trim().equals(edConfirmPass.getText().toString().trim()))
                {
                    imgConfirmPass.setVisibility(View.VISIBLE);
                    btnUpdatePassword.setBackgroundColor(btnUpdatePassword.getContext().getResources().getColor(R.color.yello));
                    btnUpdatePassword.setEnabled(true);
                }
                else {
                    imgConfirmPass.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}