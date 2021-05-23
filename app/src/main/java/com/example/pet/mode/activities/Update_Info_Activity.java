package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.mode.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Update_Info_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText edName , edPhone , edAddress , edDayOfBirth ;
    RadioGroup radioGroup ;
    View view;
    ImageButton imgBack;
    Button btnUpdateInformation ;
    RadioButton rbFemale , rbmale ;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private Calendar calendar;
    String token ;
    String gender = "male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        imgBack = findViewById(R.id.viewBackInformation);
        edAddress = findViewById(R.id.edAddress);
        edName = findViewById(R.id.edName);
        edPhone = findViewById(R.id.edPhone);
        edDayOfBirth = findViewById(R.id.edDayOfBirth);
        radioGroup = findViewById(R.id.radioG_Gender);
        rbFemale = findViewById(R.id.rbFemale);
        rbmale = findViewById(R.id.rbMale);
        btnUpdateInformation = findViewById(R.id.updateInformation);
        sharedPreferences = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","1");

        rbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
            }
        });
        rbmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="male";
            }
        });
        loadData(token);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edDayOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Update_Info_Activity.this, R.style.CustomDatePickerDialogTheme, Update_Info_Activity.this, year, month, day);
                dialog.setTitle("Chọn ngày sinh");
                dialog.show();
            }
        });
        btnUpdateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference user =  FirebaseDatabase.getInstance().getReference("Users");
                user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try{
                            user.child(token+"/phone_number").setValue(edPhone.getText().toString());
                            user.child(token+"/address").setValue(edAddress.getText().toString());
                            user.child(token+"/nick_name").setValue(edName.getText().toString());
                            user.child(token+"/gender").setValue(gender);
                            user.child(token+"/year_born").setValue(edDayOfBirth.getText().toString());
                            Toast.makeText(getApplicationContext(), "UPDATE INFORMATION SUCCESS !!! ", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(() -> {
                                finish();
                            },1000);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(), "Cập nhật không thành công ! " , Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edDayOfBirth.setText(sdf.format(calendar.getTime()));
        Log.e("TAG", "onDateSet: " + calendar.getTime() );
    }
    void loadData(String token)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                edName.setText("" + user.getNick_name());
                edAddress.setText("" + user.getAddress());
                if(user.getGender().equals("male"))
                {
                    rbmale.setChecked(true);
                    rbFemale.setChecked(false);
                }
                else {
                    rbmale.setChecked(false);
                    rbFemale.setChecked(true);
                }
                edPhone.setText("" + user.getPhone_number());
                edDayOfBirth.setText("" + user.getYear_born());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}