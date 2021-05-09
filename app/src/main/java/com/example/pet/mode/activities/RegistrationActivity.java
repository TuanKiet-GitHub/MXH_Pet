package com.example.pet.mode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Intent intent;

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
        dialog.setTitle("Chọn ngày sinh");
        dialog.show();
    }
    public void clickNext(View view) {

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
        if (registrationBinding.etFullName.getText().length() == 0) {
            Toast.makeText(this, "Bạn chưa nhập họ và tên", Toast.LENGTH_SHORT).show();
            registrationBinding.etFullName.requestFocus();
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