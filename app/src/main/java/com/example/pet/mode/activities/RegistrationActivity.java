package com.example.pet.mode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.databinding.ActivityRegistrationBinding;
import com.example.pet.mode.utils.DateFormatUtil;
import com.example.pet.mode.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ActivityRegistrationBinding registrationBinding;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registrationBinding = DataBindingUtil.setContentView(RegistrationActivity.this, R.layout.activity_registration);
        registrationBinding.setObj(RegistrationActivity.this);
        registrationBinding.layoutFirstRegister.setOnClickListener(v -> Utils.hideSoftKeyboard(RegistrationActivity.this));
    }

    public void openDatePicker(View view) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.CustomDatePickerDialogTheme, this, year, month, day);
        dialog.setTitle("Day of birth");
        dialog.show();
    }
    public void clickNext(View view) {
        Intent intent;
        if (validationInputData()) {
            intent = new Intent(RegistrationActivity.this, RegisterActivity2.class);
            Bundle bundle = new Bundle();
            bundle.putString("fullName", registrationBinding.etFullName.getText().toString());
            bundle.putString("nickName", registrationBinding.etNickName.getText().toString());
            bundle.putSerializable("yearBorn", DateFormatUtil.formatToServer(registrationBinding.etYearBorn.getText().toString()));
            bundle.putString("address", registrationBinding.etAddress.getText().toString());
            bundle.putBoolean("isMale", registrationBinding.rbMale.isChecked());
            intent.putExtra("data", bundle);
            startActivity(intent);
        }
    }
    public boolean validationInputData() {

        if (registrationBinding.etFullName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "You have not entered your name", Toast.LENGTH_SHORT).show();
            registrationBinding.etFullName.requestFocus();
            return false;
        }else if (registrationBinding.etNickName.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this, "You have not entered your nickname", Toast.LENGTH_SHORT).show();
            registrationBinding.etNickName.requestFocus();
            return false;
        } else if (registrationBinding.etAddress.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this, "You have not entered your address", Toast.LENGTH_SHORT).show();
            registrationBinding.etAddress.requestFocus();
            return false;
        }
        return true;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        registrationBinding.etYearBorn.setText(sdf.format(calendar.getTime()));
        Log.e("TAG", "onDateSet: " + calendar.getTime() );
    }
}