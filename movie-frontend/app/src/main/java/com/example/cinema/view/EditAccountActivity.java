package com.example.cinema.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountActivity extends AppCompatActivity {

    private Button btnEditAccount;
    private EditText editName, editPhone, editEmail, editBirthday;
    private RadioGroup editGender;
    private RadioButton editMale, editFemale;
    //Chú ý phần SharedPreferences
    private SharedPreferences sharedPreferences;
    private SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editBirthday = findViewById(R.id.editBirthday);
        editGender = findViewById(R.id.editGender);
        editMale = findViewById(R.id.editMale);
        editFemale = findViewById(R.id.editFemale);
        btnEditAccount = findViewById(R.id.btnEditAccount);
        //Chú ý phần SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        loadUserInfo();
        editBirthday.setOnClickListener(v -> showDatePickerDialog());
        btnEditAccount.setOnClickListener(v -> {
            editAccount();
        });
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                EditAccountActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    Date selectedDate = calendar.getTime();
                    String formattedDate = dateFormat.format(selectedDate);
                    editBirthday.setText(formattedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    private void loadUserInfo() {
        //Chú ý phần SharedPreferences
        String name = sharedPreferences.getString("userName", "");
        String phone = sharedPreferences.getString("userPhone", "");
        String email = sharedPreferences.getString("userEmail", "");
        String gender = sharedPreferences.getString("userGender", "");
        String birthdayString = sharedPreferences.getString("userBirthday", "");

        editName.setText(name);
        editPhone.setText(phone);
        editEmail.setText(email);

        if (gender.equals("nam")) {
            editMale.setChecked(true);
        } else {
            editFemale.setChecked(true);
        }
        if (!birthdayString.isEmpty()) {
            try {
                // Parse chuỗi birthdayString thành đối tượng Date
                Date birthday = inputDateFormat.parse(birthdayString);
                // Định dạng lại đối tượng Date thành chuỗi "dd-MM-yyyy"
                String formattedBirthday = dateFormat.format(birthday);
                // Hiển thị chuỗi định dạng mới lên EditText
                editBirthday.setText(formattedBirthday);
            } catch (Exception e) {
                editBirthday.setText("Chọn ngày sinh");
                e.printStackTrace();
                showAlert("Ngày sinh không hợp lệ");
                return;
            }
        }
    }

    private void editAccount() {
        String name = editName.getText().toString();
        String phone = editPhone.getText().toString();
        String email = editEmail.getText().toString();
        String gender = editMale.isChecked() ? "nam" : "nữ";
        String birthdayStr = editBirthday.getText().toString();
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || gender.isEmpty() || birthdayStr.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        String birthday = null;
        if (!birthdayStr.isEmpty()) {
            try {
                Date parsedDate = dateFormat.parse(birthdayStr);
                birthday = inputDateFormat.format(parsedDate);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Vui lòng chọn ngày sinh hợp lệ");
                return;
            }
        }
        //Chú ý phần SharedPreferences
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            new AlertDialog.Builder(this)
                    .setMessage("Thiếu ID tài khoản. Vui lòng đăng nhập lại")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
            return;
        }
        Register editAccount = new Register( name, phone, email, gender, birthday);
        String finalBirthday = birthday;
        ApiService.apiService.editUsers(userId,editAccount).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        showAlert("Cập nhật thông tin thành công");
                        // Cập nhật SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userName", name);
                        editor.putString("userPhone", phone);
                        editor.putString("userEmail", email);
                        editor.putString("userGender", gender);
                        editor.putString("userBirthday", finalBirthday);
                        editor.apply();

                        finish();
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