package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button btnBack, accountButton;
    private TextView tvTitle, tvDirector, tvActors;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvDirector = findViewById(R.id.tvDirector);
        tvActors = findViewById(R.id.tvActors);
        btnBack.setOnClickListener(v -> {
            finish();
        });
        accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainAccountActivity.class);
            startActivity(intent);
        });
        ApiService.apiService.getMoviebyId(1).enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {

                Currency currency = response.body();
                if (currency != null) {
                    tvTitle.setText(currency.getTitle());
                    tvDirector.setText(currency.getDirector());
                    tvActors.setText(currency.getActors());
                }
            }
            @Override
            public void onFailure(Call<Currency> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(MainActivity.this, "Call fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
