package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Register;
import com.example.cinema.model.SignInReponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountActivity extends AppCompatActivity {

    private Button btnEditAccount;
    private EditText editName, editPhone, editEmail;
    private RadioGroup editGender;
    private RadioButton editMale;
    private DatePicker editBirthday;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editBirthday = findViewById(R.id.editBirthday);
        editGender = findViewById(R.id.editGender);
        btnEditAccount = findViewById(R.id.btnEditAccount);
        btnEditAccount.setOnClickListener(v -> {
            editAccount();
        });
    }

    private void editAccount() {
        String name = String.valueOf(editName.getText());
        String phone = String.valueOf(editPhone.getText());
        String email = String.valueOf(editEmail.getText());
        String gender = editMale.isChecked() ? "Male" : "Female";
        DatePicker datePicker = findViewById(R.id.editBirthday);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date birthday = calendar.getTime();

        Register editAccount = new Register( name, phone, email, gender, birthday);
//        ApiService.apiService.editUsers(null,editAccount).enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse apiResponse = response.body();
//                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
//                        finish();
//                    } else {
//                        String errorMessage = apiResponse != null ? apiResponse.getMessage() : "Unknown error";
//                        new AlertDialog.Builder(EditAccountActivity.this)
//                                .setMessage(errorMessage)
//                                .setPositiveButton("OK", null)
//                                .show();
//                    }
//                } else {
//                    try {
//                        String errorBody = response.errorBody().string();
//                        new AlertDialog.Builder(EditAccountActivity.this)
//                                .setMessage(errorBody)
//                                .setPositiveButton("OK", null)
//                                .show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        new AlertDialog.Builder(EditAccountActivity.this)
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
//                new AlertDialog.Builder(EditAccountActivity.this)
//                        .setMessage("Error -> " + throwable.getMessage())
//                        .setPositiveButton("OK", null)
//                        .show();
//            }
//        });
    }
}