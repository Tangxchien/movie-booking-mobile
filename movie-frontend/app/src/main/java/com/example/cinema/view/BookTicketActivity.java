package com.example.cinema.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.R;
import com.example.cinema.adapter.BookTicketAdapter;

public class BookTicketActivity extends AppCompatActivity {
    private RecyclerView rcvBookTickets;
    private BookTicketAdapter bookTicketAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        rcvBookTickets = findViewById(R.id.rcvBookTicket);

        String[][] seat = new String[5][6];
        for (int i = 0; i < seat.length; i++) {
            for (int j = 0; j < seat[i].length; j++) {
                seat[i][j] = String.valueOf(i * seat[0].length + j);
            }
        }
        bookTicketAdapter = new BookTicketAdapter(seat);
        bookTicketAdapter.setOnItemClickListener(new BookTicketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int row, int col) {
                Toast.makeText(BookTicketActivity.this, "Clicked: (" + row + ", " + col + ")", Toast.LENGTH_SHORT).show();
            }
        });
        rcvBookTickets.setAdapter(bookTicketAdapter);
        rcvBookTickets.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false));
    }
}


