package com.example.cinema.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Register;
import com.example.cinema.model.SignIn;
import com.example.cinema.model.SignInReponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText registerName, registerPhone, registerEmail, registerPassword;
    private RadioGroup registerGender;
    private RadioButton registerMale;
    private DatePicker registerBirthday;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister);
        registerName = findViewById(R.id.registerName);
        registerPhone = findViewById(R.id.registerPhone);
        registerEmail = findViewById(R.id.registerEmail);
        registerBirthday = findViewById(R.id.registerBirthday);
        registerGender = findViewById(R.id.registerGender);
        registerMale = findViewById(R.id.registerMale);
        registerPassword = findViewById(R.id.registerPassword);
        btnRegister.setOnClickListener(v -> {
            checkRegister();
        });
    }
    private void checkRegister() {
        String name = String.valueOf(registerName.getText());
        String phone = String.valueOf(registerPhone.getText());
        String email = String.valueOf(registerEmail.getText());
        String password = String.valueOf(registerPassword.getText());
        String gender = registerMale.isChecked() ? "Male" : "Female";
        DatePicker datePicker = findViewById(R.id.registerBirthday);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date birthday = calendar.getTime();

        Register register = new Register( name, phone, email, gender, password, birthday);
        ApiService.apiService.registerUsers(register).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        String errorMessage = apiResponse != null ? apiResponse.getMessage() : "Unknown error";
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setMessage(errorBody)
                                .setPositiveButton("OK", null)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setMessage("Error: " + e.getMessage())
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                new AlertDialog.Builder(RegisterActivity.this)
                        .setMessage("Error -> " + throwable.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
