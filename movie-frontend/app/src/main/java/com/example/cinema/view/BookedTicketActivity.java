package com.example.cinema.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.R;
import com.example.cinema.adapter.TicketInfoAdapter;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.TicketInfo;

import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedTicketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketInfoAdapter ticketInfoAdapter;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_ticket);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);


        showBookedTicket();
    }

    private void showBookedTicket() {
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            new AlertDialog.Builder(this)
                    .setMessage("User ID is missing. Please log in again.")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
            return;
        }
        ApiService.apiService.getTicketByAccountId(userId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        ArrayList<TicketInfo> tickets = (ArrayList<TicketInfo>) apiResponse.getData();
                        ticketInfoAdapter = new TicketInfoAdapter(BookedTicketActivity.this, tickets);
                        recyclerView.setAdapter(ticketInfoAdapter);
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
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                new AlertDialog.Builder(BookedTicketActivity.this)
                        .setMessage("Request failed: " + t.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
