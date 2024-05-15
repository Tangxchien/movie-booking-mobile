package com.example.cinema.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.R;
import com.example.cinema.model.TicketInfo;

import java.util.ArrayList;

public class TicketInfoAdapter extends RecyclerView.Adapter<TicketInfoAdapter.TicketInfoViewHolder> {

    private ArrayList<TicketInfo> tickets;

    public TicketInfoAdapter(ArrayList<TicketInfo> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_booked_ticket, parent, false);
        return new TicketInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketInfoViewHolder holder, int position) {
        TicketInfo ticket = tickets.get(position);

        holder.textViewMovieTitle.setText(ticket.getMovieTitle());
        holder.textViewCinemaName.setText(ticket.getCinemaName());
        holder.textViewShowTime.setText(ticket.getShowTime());
        if (ticket.getSeats() != null && !ticket.getSeats().isEmpty()) {
            holder.textViewSeats.setText(TextUtils.join(", ", ticket.getSeats()));
        } else {
            holder.textViewSeats.setText("No seats booked");
        }    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class TicketInfoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMovieTitle, textViewCinemaName, textViewShowTime, textViewSeats;

        public TicketInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMovieTitle = itemView.findViewById(R.id.textViewMovieTitle);
            textViewCinemaName = itemView.findViewById(R.id.textViewCinemaName);
            textViewShowTime = itemView.findViewById(R.id.textViewShowTime);
            textViewSeats = itemView.findViewById(R.id.textViewSeats);
        }
    }
}
