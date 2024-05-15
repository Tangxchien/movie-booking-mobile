package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText editCurrentPassword, editNewPassword, editConfirmPassword;
    private Button btnChangePassword;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editCurrentPassword = findViewById(R.id.editPassword);
        editNewPassword = findViewById(R.id.editNewPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(v -> {
            changePassword();
        });
    }

    private void changePassword() {
//        String currentPassword = editCurrentPassword.getText().toString().trim();
//        String newPassword = editNewPassword.getText().toString().trim();
//        String confirmPassword = editConfirmPassword.getText().toString().trim();
//
//        ApiService.apiService.changePassword(id, currentPassword, newPassword).enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse apiResponse = response.body();
//                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
//                        finish();
//                    } else {
//                        String errorMessage = apiResponse != null ? apiResponse.getMessage() : "Unknown error";
//                        new AlertDialog.Builder(ChangePasswordActivity.this)
//                                .setMessage(errorMessage)
//                                .setPositiveButton("OK", null)
//                                .show();
//                    }
//                } else {
//                    try {
//                        String errorBody = response.errorBody().string();
//                        new AlertDialog.Builder(ChangePasswordActivity.this)
//                                .setMessage(errorBody)
//                                .setPositiveButton("OK", null)
//                                .show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        new AlertDialog.Builder(ChangePasswordActivity.this)
//                                .setMessage("Error: " + e.getMessage())
//                                .setPositiveButton("OK", null)
//                                .show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
//                throwable.printStackTrace();
//                new AlertDialog.Builder(ChangePasswordActivity.this)
//                        .setMessage("Error -> " + throwable.getMessage())
//                        .setPositiveButton("OK", null)
//                        .show();
//            }
//        });
    }
}