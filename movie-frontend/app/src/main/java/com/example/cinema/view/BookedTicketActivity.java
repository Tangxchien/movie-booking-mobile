package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;

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

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_ticket);
        showBookedTicket();
    }

    private void showBookedTicket() {
        //Sửa chố id này
        ApiService.apiService.getTicketByAccountId(1).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
                    } else {
                        String errorMessage = apiResponse != null ? apiResponse.getMessage() : "Unknown error";
                        new AlertDialog.Builder(BookedTicketActivity.this)
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        new AlertDialog.Builder(BookedTicketActivity.this)
                                .setMessage(errorBody)
                                .setPositiveButton("OK", null)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(BookedTicketActivity.this)
                                .setMessage("Error: " + e.getMessage())
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                new AlertDialog.Builder(BookedTicketActivity.this)
                        .setMessage("Error -> " + throwable.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}