package com.example.cinema.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cinema.R;
import com.example.cinema.callback.OnClickItem;
import com.example.cinema.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> mListMovie;
    private OnClickItem onClickItem;

    public MovieAdapter(List<Movie> mListMovie, OnClickItem onClickItem) {
        this.mListMovie = mListMovie;
        this.onClickItem = onClickItem;
    }

    public Movie getData(int position) {
      return mListMovie.get(position);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mListMovie.get(position);
        if(movie == null){
            return;
        }
        holder.tvTitle.setText(movie.getTitle());
        Picasso.get().load(movie.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.imgMovie);
    }

    @Override
    public int getItemCount() {
        if(mListMovie != null){
            return mListMovie.size();
        }
        return 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private ImageView imgMovie;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.imgMovie);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(v -> {
                onClickItem.onClickItem(getAdapterPosition());
            });
        }
    }
}
