package com.example.cinema.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editCurrentPassword = findViewById(R.id.editPassword);
        editNewPassword = findViewById(R.id.editNewPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        btnChangePassword.setOnClickListener(v -> {
            changePassword();
        });
    }

    private void changePassword() {
        String currentPassword = editCurrentPassword.getText().toString().trim();
        String newPassword = editNewPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();
        if (currentPassword.isEmpty() || confirmPassword.isEmpty() || newPassword.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Mật khẩu mới và xác nhận mật khẩu mới không trùng khớp");
            return;
        }
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            new AlertDialog.Builder(this)
                    .setMessage("Thiếu ID tài khoản. Vui lòng đăng nhập lại")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
            return;
        }
        ApiService.apiService.changePassword(userId, currentPassword, newPassword).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
//                        showAlert("Đổi mật khẩu thành công");
                        new AlertDialog.Builder(ChangePasswordActivity.this)
                                .setMessage("Đổi mật khẩu thành công")
                                .setPositiveButton("OK", (dialog, which) -> finish())
                                .show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userPassword", newPassword);
                        editor.apply();
//                        finish();
                    } else {
                        String errorMessage = apiResponse != null ? apiResponse.getMessage() : "Lỗi không xác định";
                        showAlert(errorMessage);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        showAlert(errorBody);
                    } catch (IOException e) {
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
    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}