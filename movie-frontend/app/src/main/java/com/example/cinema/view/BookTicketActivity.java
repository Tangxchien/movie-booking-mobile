package com.example.cinema.view;

import static com.example.cinema.api.ApiService.apiService;

import android.content.Context;
import android.content.Intent;
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
import com.example.cinema.model.BookTicket;
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
    private int price, accountid, totalprice;
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
        accountid = sharedPreferences.getInt("userId", 0);
        btnPay.setOnClickListener(v -> {
            if(totalprice == 0){
                new AlertDialog.Builder(BookTicketActivity.this).setIcon(R.drawable.warning).setPositiveButton("OK", null).setTitle("THANH TOÁN THẤT BẠI").setMessage("Bạn chưa chọn ghế").show();
            } else {
                bookTicket();
            }
        });

        price = getIntent().getIntExtra("price", 0);
        name = getIntent().getStringExtra("name");
        showTime = getIntent().getStringExtra("showTime");
        showTimeId = String.valueOf(getIntent().getCharExtra("showTimeId", '0'));
        movieid = String.valueOf(getIntent().getIntExtra("id", 0));

        Intent intent = new Intent();
        ArrayList<String> seatListA = new ArrayList<>(seatList);
        intent.putStringArrayListExtra("seatList", seatListA);
        intent.putExtra("totalPrice", totalprice);

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
//            new AlertDialog.Builder(BookTicketActivity.this).setMessage(bookTicketAdapter.getSelectedSeats().toString()).show();
            rcvBookTickets.setAdapter(bookTicketAdapter);
            rcvBookTickets.setLayoutManager(new GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false));
        });

    }

    private void updateTotal(int selectedSeatsCount) {
        totalprice = selectedSeatsCount * price;
        tvTotalSeat.setText("Số ghế: " + selectedSeatsCount);
        tvTotalPrice.setText(totalprice + "đ");
    }



    private void callApiGetSeats(String movieId, String showTimeId, OnSetSeatSell onSetSeatSell) {
        ApiService.apiService.getSeats(movieId, showTimeId).enqueue(new Callback<ApiResponse<List<String>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<String>>> call, Response<ApiResponse<List<String>>> response) {

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

    private void bookTicket(){
        BookTicket bookTicket = new BookTicket(accountid, Integer.parseInt(showTimeId) , seatList, totalprice);
        apiService.bookTicket(bookTicket).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {
                        Intent i = new Intent(BookTicketActivity.this, PaymentActivity.class);
                        i.putExtra("showTime", showTime);
                        i.putExtra("MovieName", name);
                        i.putExtra("totalPrice", totalprice);
                        i.putStringArrayListExtra("seatList", new ArrayList<>(seatList));
                        startActivity(i);
                    } else {
                        new AlertDialog.Builder(BookTicketActivity.this)
                                .setMessage("Failed to book ticket: " + apiResponse.getMessage())
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(BookTicketActivity.this)
                            .setMessage("Failed to book ticket: " + response.message())
                            .setPositiveButton("OK", null)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                new AlertDialog.Builder(BookTicketActivity.this)
                        .setMessage("Error -> " + throwable.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }

}
