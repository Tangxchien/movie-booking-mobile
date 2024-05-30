package com.example.cinema.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.ForgotPasswordRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText editPhone, editEmail, editNewPassword, editConfirmPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editNewPassword = findViewById(R.id.editNewPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> submitForgotPassword());
//        btnBackToLogin.setOnClickListener(v -> finish());
    }

    private void submitForgotPassword() {
        String phone = editPhone.getText().toString();
        String email = editEmail.getText().toString();
        String newPassword = editNewPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();
        if (phone.isEmpty() || email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Mật khẩu mới và xác nhận mật khẩu mới không trùng khớp");
        }

        ForgotPasswordRequest request = new ForgotPasswordRequest(phone, email, newPassword);

        ApiService.apiService.forgotPassword(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        showAlert("Mật khẩu đã được đặt lại thành công");
                        new AlertDialog.Builder(ForgetPasswordActivity.this)
                                .setMessage("Mật khẩu đã được đặt lại thành công")
                                .setPositiveButton("OK", (dialog, which) -> finish())
                                .show();
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