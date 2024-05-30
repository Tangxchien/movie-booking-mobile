package com.example.cinema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinema.R;
import com.example.cinema.callback.OnSetSeatSell;
import com.example.cinema.view.BookTicketActivity;

import java.util.ArrayList;
import java.util.List;

public class BookTicketAdapter extends RecyclerView.Adapter<BookTicketAdapter.BookTicketViewHolder> {

    private String[][] seat;
    private boolean[][] seatSelected;
    private OnItemClickListener onItemClickListener;
    private static final char[] ROW_LETTERS = {'A', 'B', 'C', 'D', 'E', 'F'};
    private List<String> redSeat = new ArrayList<>();

    public BookTicketAdapter(String[][] seat, List<String> redSeat) {
        this.seat = seat;
        this.seatSelected = new boolean[seat.length][seat[0].length];
        this.redSeat = redSeat;
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
    public List<String> getSelectedSeats() {
        List<String> selectedSeats = new ArrayList<>();
        for (int row = 0; row < seatSelected.length; row++) {
            for (int col = 0; col < seatSelected[row].length; col++) {
                if (seatSelected[row][col]) {
                    selectedSeats.add(ROW_LETTERS[row] + String.valueOf(col + 1));
                }
            }
        }
        return selectedSeats;
    }
    public void updateSelectedSeats(List<String> selectedSeats) {
        for (String selectedSeat : selectedSeats) {
            int row = selectedSeat.charAt(0) - 'A';
            int col = Integer.parseInt(selectedSeat.substring(1)) - 1;
            seatSelected[row][col] = false;
        }
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull BookTicketViewHolder holder, int position) {
        int row = position / seat[0].length;
        int col = position % seat[0].length;

        String seatId = ROW_LETTERS[row] + String.valueOf(col + 1);

        if (redSeat.contains(seatId)) {
            holder.imgSeatId.setImageResource(R.drawable.img_seat_sell);
        } else if (seatSelected[row][col]) {
            holder.imgSeatId.setImageResource(R.drawable.img_seat_selected);
        }else {
            holder.imgSeatId.setImageResource(R.drawable.img_seat); // Đặt ảnh ghế chưa chọn và chưa có trong API
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(row, col);
                }
                String selected = ROW_LETTERS[row] + String.valueOf(col + 1);
                if (!redSeat.contains(selected)) {
                    seatSelected[row][col] = !seatSelected[row][col];
                    notifyItemChanged(position);
                }


                if (onItemClickListener != null) {
                    onItemClickListener.onSelectionChanged(getSelectedSeatsCount(), getSelectedSeats());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return seat.length * seat[0].length;
    }

    public int getSelectedSeatsCount() {
        int count = 0;
        for (boolean[] row : seatSelected) {
            for (boolean selected : row) {
                if (selected) count++;
            }
        }
        return count;
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
        void onSelectionChanged(int selectedSeatsCount, List<String> selectedSeats);
    }

}
