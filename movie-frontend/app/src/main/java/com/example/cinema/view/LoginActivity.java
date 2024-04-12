package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.Currency;
import com.example.cinema.model.SignIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText edUser, edPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnLogin);
        edUser = findViewById(R.id.edUser);
        edPassword = findViewById(R.id.edPassword);
        btnLogin.setOnClickListener(v -> {
            checkLogin();

        });

    }
    private void checkLogin(){
        String user = String.valueOf(edUser.getText());
        String pass = String.valueOf(edPassword.getText());
        SignIn signIn = new SignIn(user, pass);
        ApiService.apiService.loginUsers(signIn).enqueue(new Callback<SignIn>() {
            @Override
            public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                if(response.code() == 200) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (response.code() == 401){
                    Toast.makeText(LoginActivity.this, "Sai password", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LoginActivity.this, "Lỗi khác", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignIn> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(LoginActivity.this, "Call Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
