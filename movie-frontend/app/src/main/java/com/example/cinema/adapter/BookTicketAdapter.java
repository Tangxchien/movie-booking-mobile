package com.example.cinema.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.R;

public class BookTicketAdapter extends RecyclerView.Adapter<BookTicketAdapter.BookTicketViewHolder> {

    private String[][] seat;
    private OnItemClickListener onItemClickListener;

    public BookTicketAdapter(String[][] seat) {
        this.seat = seat;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BookTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new BookTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookTicketViewHolder holder, int position) {
        int row = position / seat[0].length;
        int col = position % seat[0].length;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(row, col);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seat.length * seat[0].length;
    }

    public static class BookTicketViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSeatId;

        public BookTicketViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSeatId = itemView.findViewById(R.id.imgSeatId);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int row, int col);
    }
}
