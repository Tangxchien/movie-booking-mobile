package com.example.cinema.view;

import static com.example.cinema.api.ApiService.apiService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinema.R;
import com.example.cinema.adapter.BookTicketAdapter;
import com.example.cinema.api.ApiService;
import com.example.cinema.callback.OnSetSeatSell;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Seat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookTicketActivity extends AppCompatActivity {
    private RecyclerView rcvBookTickets;
    private BookTicketAdapter bookTicketAdapter;
    private TextView tvTitleSeat, tvTotalPrice, tvTotalSeat, tvShowTime;
    private int price;
    private String movieid, name, showTime,showTimeId;
    private SharedPreferences sharedPreferences;
    private Button btnPay;
    private List<String> seatList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        rcvBookTickets = findViewById(R.id.rcvBookTicket);
        tvTitleSeat = findViewById(R.id.tvTitleSeat);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvTotalSeat = findViewById(R.id.tvTotalSeat);
        tvShowTime = findViewById(R.id.tvShowTime);
        btnPay = findViewById(R.id.btnPay);

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        btnPay.setOnClickListener(v -> {
            Toast.makeText(this, sharedPreferences.getString("userId", ""), Toast.LENGTH_SHORT).show();
        });

        price = getIntent().getIntExtra("price", 0);
        name = getIntent().getStringExtra("name");
        showTime = getIntent().getStringExtra("showTime");
        showTimeId = String.valueOf(getIntent().getCharExtra("showTimeId", '0'));
        movieid = String.valueOf(getIntent().getIntExtra("id", 0));

        tvTitleSeat.setText(name);
        tvShowTime.setText(showTime);

        String[][] seat = new String[6][6];
        for (int i = 0; i < seat.length; i++) {
            for (int j = 0; j < seat[i].length; j++) {
                seat[i][j] = String.valueOf(i * seat[0].length + j);
            }
        }

        callApiGetSeats(movieid, showTimeId, seatSells -> {
            bookTicketAdapter = new BookTicketAdapter(seat, seatSells);
            bookTicketAdapter.updateSelectedSeats(seatSells);
            bookTicketAdapter.setOnItemClickListener(new BookTicketAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int row, int col) {
//                    Toast.makeText(BookTicketActivity.this, "Đã chọn ghế (" + (char) ('A' + row) + (col + 1) + ")", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSelectionChanged(int selectedSeatsCount, List<String> selectedSeats) {
                    seatList.clear();
                    seatList.addAll(selectedSeats);
                    updateTotal(selectedSeatsCount);
                }
            });
            new AlertDialog.Builder(BookTicketActivity.this).setMessage(bookTicketAdapter.getSelectedSeats().toString()).show();
            rcvBookTickets.setAdapter(bookTicketAdapter);
            rcvBookTickets.setLayoutManager(new GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false));
        });

    }

    private void updateTotal(int selectedSeatsCount) {
        int totalPrice = selectedSeatsCount * price;
        tvTotalSeat.setText("Số ghế: " + selectedSeatsCount);
        tvTotalPrice.setText(totalPrice + "đ");
    }

    private void callApiGetSeats(String movieId, String showTimeId, OnSetSeatSell onSetSeatSell) {
        ApiService.apiService.getSeats(movieId, showTimeId).enqueue(new Callback<ApiResponse<List<String>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<String>>> call, Response<ApiResponse<List<String>>> response) {
//                AlertDialog alertDialog = new AlertDialog.Builder(BookTicketActivity.this).setMessage(response.toString()).show();

                    List<String> selectedSeats = response.body().getData();
                    if (selectedSeats != null) {
                        onSetSeatSell.OnSeatSell(selectedSeats);
                    }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<String>>> call, Throwable t) {
                Toast.makeText(BookTicketActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
