package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.databinding.ActivityLoginBinding;
import com.example.pet.mode.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        loginBinding.edtPassword.setOnFocusChangeListener((view, b) -> loginBinding.edtPassword.setHint(""));
        loginBinding.edtEmail.setOnFocusChangeListener((view, b) -> loginBinding.edtEmail.setHint(""));

        loginBinding.btnLogin.setOnClickListener(v -> {
            String email = loginBinding.edtEmail.getText().toString().trim();
            String passWord = loginBinding.edtPassword.getText().toString().trim();

            if (email.isEmpty()) {
                //     Toast.makeText(Login.this, "Name " + name, Toast.LENGTH_LONG).show();
                Toast.makeText(LoginActivity.this, "Mật khẩu không được bỏ trống !!! ", Toast.LENGTH_LONG).show();
                loginBinding.edtEmail.requestFocus();
                return;
            }
            if (passWord.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Mật khẩu không được bỏ trống !!! ", Toast.LENGTH_LONG).show();
                loginBinding.edtPassword.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplication(), HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "ĐĂNG NHẬP THẤT BẠI !!! ", Toast.LENGTH_LONG).show();
                    }
                }
            });
        });

        loginBinding.tvRegister.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        loginBinding.layout.setOnClickListener(v -> Utils.hideSoftKeyboard(LoginActivity.this));
    }


}