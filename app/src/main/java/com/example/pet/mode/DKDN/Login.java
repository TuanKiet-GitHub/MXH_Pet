package com.example.pet.mode.DKDN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText edEmail, edPassWord;
    Button btnLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        anhXa();
        eventLogin();
        eventEditText();
    }

    // region Event btnLogin
    void eventLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String passWord = edPassWord.getText().toString().trim();

                if (email.isEmpty()) {
                    //     Toast.makeText(Login.this, "Name " + name, Toast.LENGTH_LONG).show();
                    Toast.makeText(Login.this, "Mật khẩu không được bỏ trống !!! " , Toast.LENGTH_LONG).show();
                    edEmail.requestFocus();
                    return;
                }
                if (passWord.isEmpty()) {
                    Toast.makeText(Login.this, "Mật khẩu không được bỏ trống !!! " , Toast.LENGTH_LONG).show();
                    edPassWord.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplication(), Home.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "ĐĂNG NHẬP THẤT BẠI !!! ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    // endregion

    // region    Sự kiện editText

    void eventEditText() {
        edPassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                edPassWord.setHint("");
            }
        });
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                edEmail.setHint("");
            }
        });
    }
    // endregion

    //region ANH XẠ
    void anhXa() {
        btnLogin = findViewById(R.id.btnDangNhap);
        edEmail = findViewById(R.id.edEmail);
        //   edTen.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edPassWord = findViewById(R.id.edMk);
//        edMk.setInputType(InputType.TYPE_CLASS_TEXT |
//                InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    // endregion
}