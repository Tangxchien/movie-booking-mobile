package com.example.cinema.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cinema.R;

public class MainAccountActivity extends AppCompatActivity {
    private TextView usernameTextView, editAccountLink, changePasswordLink, bookedTicketLink, logoutLink;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);


        usernameTextView = findViewById(R.id.usernameTextView);
        editAccountLink = findViewById(R.id.editAccountLink);
        changePasswordLink = findViewById(R.id.changePasswordLink);
        bookedTicketLink = findViewById(R.id.bookedTicketLink);
        logoutLink = findViewById(R.id.logoutLink);

        // Lấy tên người dùng từ SharedPreferences
        updateUsername();

        editAccountLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainAccountActivity.this, EditAccountActivity.class);
            startActivity(intent);
        });

        changePasswordLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainAccountActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        bookedTicketLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainAccountActivity.this, BookedTicketActivity.class);
            startActivity(intent);
        });
        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa thông tin người dùng khỏi SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Chuyển về MainActivity hoặc trang đăng nhập
                Intent intent = new Intent(MainAccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật tên người dùng từ SharedPreferences
        updateUsername();
    }

    private void updateUsername() {
        String username = sharedPreferences.getString("userName", "Guest");
        usernameTextView.setText(username);
    }
}