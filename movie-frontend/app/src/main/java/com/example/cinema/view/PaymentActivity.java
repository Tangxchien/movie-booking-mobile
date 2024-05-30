package com.example.cinema.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private Button btnBackHome;
    private TextView tvMoviePriceV, tvMovieSeatV, tvMovieTimeV, tvMovieName;
    private String showTime, name, totalPrice;
    private ArrayList<String> seatList;
    private Intent i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tvMoviePriceV = findViewById(R.id.tvMoviePriceV);
        tvMovieSeatV = findViewById(R.id.tvMovieSeatV);
        tvMovieTimeV = findViewById(R.id.tvMovieTimeV);
        tvMovieName = findViewById(R.id.tvMovieName);
        btnBackHome = findViewById(R.id.btnBackHome);

        showTime = getIntent().getStringExtra("showTime");
        name = getIntent().getStringExtra("MovieName");
        totalPrice = String.valueOf(getIntent().getIntExtra("totalPrice", 0));
        seatList = getIntent().getStringArrayListExtra("seatList");
        if (seatList != null) {
            String seatListString = TextUtils.join(", ", seatList);
            tvMovieSeatV.setText(seatListString);
        }
        tvMoviePriceV.setText(totalPrice + "đ");
        tvMovieTimeV.setText(showTime);
        tvMovieName.setText("PHIM :" + name.toUpperCase());
        btnBackHome.setOnClickListener(v -> {
            i = new Intent(PaymentActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}
