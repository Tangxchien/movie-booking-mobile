package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;

public class PaymentActivity extends AppCompatActivity {
    private Button btnBackHome;
    private TextView tvMoviePriceV, tvMovieSeatV, tvMovieTimeV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tvMoviePriceV = findViewById(R.id.tvMoviePriceV);
        tvMovieSeatV = findViewById(R.id.tvMovieSeatV);
        tvMovieTimeV = findViewById(R.id.tvMovieTimeV);
        btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(v -> {
            Intent i = new Intent(PaymentActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}
