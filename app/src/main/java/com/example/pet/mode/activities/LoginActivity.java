package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.databinding.ActivityLoginBinding;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private ActivityLoginBinding loginBinding;
    private SharedPreferences sharedPreferences;
    private final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String token = Utils.getToken(LoginActivity.this);
        if (token != null && !token.equals("1")) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        loginBinding.edtPassword.setOnFocusChangeListener((view, b) -> loginBinding.edtPassword.setHint(""));
        loginBinding.edtEmail.setOnFocusChangeListener((view, b) -> loginBinding.edtEmail.setHint(""));

        loginBinding.btnLogin.setOnClickListener(v -> {

            if (checkEmailPassword()) {
                String email = loginBinding.edtEmail.getText().toString().trim();
                String passWord = loginBinding.edtPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener(task -> {
                    disableTouchScreen();
                    loginBinding.pbRegisterSecond.setVisibility(View.VISIBLE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        assert user != null;
                        editor.putString("token", user.getUid());
                        editor.putString("password", passWord);
                        //saveInformUser(user.getUid());
                        editor.apply();
                        Intent intent = new Intent(getApplication(), HomeActivity.class);
                        startActivity(intent);
                    } else {
                        enableTouchScreen();
                        loginBinding.pbRegisterSecond.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Password or email incorrect !!", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                enableTouchScreen();
                loginBinding.pbRegisterSecond.setVisibility(View.GONE);
            }


        });

        loginBinding.tvRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));

        loginBinding.layout.setOnClickListener(v -> Utils.hideSoftKeyboard(LoginActivity.this));
    }

    private boolean checkEmailPassword() {
        String email = loginBinding.edtEmail.getText().toString().trim();
        String passWord = loginBinding.edtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email cannot be empty !!!", Toast.LENGTH_LONG).show();
            loginBinding.edtEmail.requestFocus();
            return false;
        } else if (passWord.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Password cannot be empty !!!", Toast.LENGTH_LONG).show();
            loginBinding.edtPassword.requestFocus();
            return false;
        }
        return true;
    }
    
}