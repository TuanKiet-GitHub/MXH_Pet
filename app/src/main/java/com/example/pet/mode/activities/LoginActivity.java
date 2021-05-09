package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private ActivityLoginBinding loginBinding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        loginBinding.edtPassword.setOnFocusChangeListener((view, b) -> loginBinding.edtPassword.setHint(""));
        loginBinding.edtEmail.setOnFocusChangeListener((view, b) -> loginBinding.edtEmail.setHint(""));

        loginBinding.btnLogin.setOnClickListener(v -> {
            if(checkEmailPassword()){
                String email = loginBinding.edtEmail.getText().toString().trim();
                String passWord = loginBinding.edtPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        disableTouchScreen();
                        loginBinding.pbRegisterSecond.setVisibility(View.VISIBLE);

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", user.getUid());
                            editor.apply();
                            Intent intent = new Intent(getApplication(), HomeActivity.class);
                            startActivity(intent);
                        } else {

                            Toast.makeText(LoginActivity.this, "Mật khẩu hoặc tài khoản không chính xác", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else {
                enableTouchScreen();
                loginBinding.pbRegisterSecond.setVisibility(View.GONE);
            }


        });

        loginBinding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        loginBinding.layout.setOnClickListener(v -> Utils.hideSoftKeyboard(LoginActivity.this));
    }

    private boolean checkEmailPassword() {
        String email = loginBinding.edtEmail.getText().toString().trim();
        String passWord = loginBinding.edtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Mật khẩu không được bỏ trống !!! ", Toast.LENGTH_LONG).show();
            loginBinding.edtEmail.requestFocus();
            return false;
        } else if (passWord.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Mật khẩu không được bỏ trống !!! ", Toast.LENGTH_LONG).show();
            loginBinding.edtPassword.requestFocus();
            return false;
        }
        return true;
    }

}