package com.example.cinema.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Currency;
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

        // Initialize SharedPreferences
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
        SignIn signIn = new SignIn(phone, pass);
        ApiService.apiService.loginUsers(signIn).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {

                        LinkedTreeMap dataMap = (LinkedTreeMap) apiResponse.getData();
                        // Chuyển đổi Map thành JSON String
                        String jsonData = gson.toJson(dataMap);
                        SignInReponse userLoggedIn = gson.fromJson(jsonData, SignInReponse.class);
                        saveUserInfo(userLoggedIn);


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        String errorMessage = apiResponse != null ? apiResponse.getMessage() : "Unknown error";
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(errorBody)
                                .setPositiveButton("OK", null)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("Error: " + e.getMessage())
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("Error -> " + throwable.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

    }

    private void saveUserInfo(SignInReponse user) {
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
}
