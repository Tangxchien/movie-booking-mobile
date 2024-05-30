package com.example.cinema.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//Chú ý phần SharedPreferences
import android.content.SharedPreferences;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
//import com.example.cinema.model.Currency;
import com.example.cinema.model.SignIn;
import com.example.cinema.model.SignInReponse;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText edUser, edPassword;
    private TextView tvForgetPassword;
    //Chú ý phần SharedPreferences
    private SharedPreferences sharedPreferences;

    private TextView tvForgetPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edUser = findViewById(R.id.edUser);
        edPassword = findViewById(R.id.edPassword);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvForgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });

        //Chú ý phần SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            checkLogin();
        });
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void checkLogin(){
        String phone = String.valueOf(edUser.getText());
        String pass = String.valueOf(edPassword.getText());
        if (phone.isEmpty() || pass.isEmpty()) {
            showAlert("Vui lòng nhập cả số điện thoại và mật khẩu.");
            return;
        }
        SignIn signIn = new SignIn(phone, pass);
        ApiService.apiService.loginUsers(signIn).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (response.isSuccessful()) {
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        Gson gson = new Gson();
                        LinkedTreeMap dataMap = (LinkedTreeMap) apiResponse.getData();
                        // Chuyển đổi Map thành JSON String
                        String jsonData = gson.toJson(dataMap);
                        SignInReponse userLoggedIn = gson.fromJson(jsonData, SignInReponse.class);
                        saveUserInfo(userLoggedIn);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
//                        finish();
                    } else {
                        String errorMessage = apiResponse != null ? apiResponse.getMessage() : "Lỗi không xác định";
                        showAlert(errorMessage);
                    }
                } else {
                    try {
                        String errorBody = response.body().getMessage();
                        showAlert(errorBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Error: " + e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                showAlert("Error -> " + throwable.getMessage());
            }
        });

    }

    private void saveUserInfo(SignInReponse user) {
        //Chú ý phần SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", user.getId());
        editor.putString("userName", user.getName());
        editor.putString("userPhone", user.getPhone());
        editor.putString("userEmail", user.getEmail());
        editor.putString("userGender", user.getGender());
        editor.putString("userPassword", user.getPassword());
        editor.putString("userBirthday", user.getBirthday());
        editor.apply();
    }
    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void checkRegister() {
    }
}
