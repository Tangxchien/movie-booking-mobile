package com.example.cinema.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.R;
import com.example.cinema.model.TicketInfo;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TicketInfoAdapter extends RecyclerView.Adapter<TicketInfoAdapter.TicketViewHolder> {

    private List<TicketInfo> ticketList;
    private Context context;

    public TicketInfoAdapter(Context context, List<TicketInfo> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_booked_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketInfo ticket = ticketList.get(position);
        holder.movieTitle.setText(ticket.getMovieTitle());
        String showTimeFormatted = "Giờ chiếu: " + new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(ticket.getShowTime());
        holder.showTime.setText(showTimeFormatted);

        // Định dạng danh sách ghế
        String seatsFormatted = "Ghế: " + String.join(", ", ticket.getSeats());
        holder.seats.setText(seatsFormatted);

        if (ticket.getMovieImage() != null && !ticket.getMovieImage().isEmpty()) {
            Picasso.get().load(ticket.getMovieImage()).placeholder(R.drawable.ic_launcher_background).into(holder.movieImage);
        } else {
            holder.movieImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitle;
//        TextView cinemaName;
        TextView showTime;
        TextView seats;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImage);
            movieTitle = itemView.findViewById(R.id.movieTitle);
//            cinemaName = itemView.findViewById(R.id.cinemaName);
            showTime = itemView.findViewById(R.id.showTime);
            seats = itemView.findViewById(R.id.seats);
        }
    }
}
