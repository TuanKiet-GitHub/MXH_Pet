package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.databinding.ActivityRegister2Binding;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.CheckDataUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity2 extends BaseActivity {
    ActivityRegister2Binding mBinding;
    private User user;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String isMale;
    private String email, password, confirmPassword, phoneNumber, fullName, yearBorn, address, nickName, userId;
    private String TAG = "a";
    private HashMap<String, String> userInfor;
    private SharedPreferences sharedPreferences;
    private Button btPrevious;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(RegisterActivity2.this, R.layout.activity_register2);
        user = new User();

        btPrevious = findViewById(R.id.btPrevious);
        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void clickRegister(View v) {

        getUser();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        if (checkInformation()) {
            mBinding.pbRegisterSecond.setVisibility(View.VISIBLE);
            disableTouchScreen();
            mAuth.createUserWithEmailAndPassword(mBinding.etEmail.getText().toString(), mBinding.etPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser mUser = mAuth.getCurrentUser();
                                user.setId(mUser.getUid());
                                sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", mUser.getUid());
                                editor.apply();
                                userInfor = getData();
                                reference.child(mUser.getUid()).setValue(userInfor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity2.this, "Login successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity2.this, HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        } else
                                            Log.e(TAG, "onComplete: " + task.getException().getMessage());
                                    }
                                });

                            } else {
                                Toast.makeText(RegisterActivity2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            mBinding.pbRegisterSecond.setVisibility(View.GONE);
            enableTouchScreen();
        }
    }

    private void getUser() {
        password = mBinding.etPassword.getText().toString().trim();
        phoneNumber = mBinding.etPhoneNumber.getText().toString().trim();
        email = mBinding.etEmail.getText().toString().trim();
        confirmPassword = mBinding.etConfirmPassword.getText().toString().trim();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");

        if (bundle != null) {
            fullName = bundle.getString("fullName");
            address = bundle.getString("address");
            isMale = bundle.getBoolean("isMale") ? "male" : "female";
            yearBorn = bundle.getString("yearBorn");
            nickName = bundle.getString("nickName");
        }

        user.setId(userId);
        user.setNick_name(nickName);
        user.setYear_born(yearBorn);
        user.setGender(isMale);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhone_number(phoneNumber);
        user.setFull_name(fullName);
        user.setAvatar("a");
    }

    private boolean checkInformation() {

        if (email.isEmpty()) {
            Toast.makeText(this, "Email không thể rỗng", Toast.LENGTH_SHORT).show();
            mBinding.etEmail.requestFocus();
            return false;
        } else if (!CheckDataUtil.checkEmail(email)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            mBinding.etEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            mBinding.etPassword.requestFocus();
            return false;
        } else if (password.length() < 7) {
            Toast.makeText(this, "Mật khẩu phải hơn 7 ký tự", Toast.LENGTH_SHORT).show();
            mBinding.etPassword.setText("");
            mBinding.etPassword.requestFocus();
            return false;
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            mBinding.etConfirmPassword.setText("");
            mBinding.etConfirmPassword.requestFocus();
            return false;
        } else if (!phoneNumber.isEmpty() && !CheckDataUtil.checkPhone(phoneNumber)) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            mBinding.etPhoneNumber.requestFocus();
            return false;
        }

        return true;
    }

    private HashMap<String, String> getData() {
        HashMap<String, String> temp = new HashMap<>();
        temp.put("id", user.getId());
        temp.put("full_name", user.getFull_name());
        temp.put("nick_name", user.getNick_name());
        temp.put("address", user.getAddress());
        temp.put("phone_number", user.getPhone_number());
        temp.put("year_born", user.getYear_born());
        temp.put("gender", user.getGender());
        temp.put("email", user.getEmail());

        return temp;
    }
}