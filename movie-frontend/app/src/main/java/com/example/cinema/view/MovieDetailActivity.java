package com.example.cinema.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvId, tvTitle, tvActors;
    ImageView imgMovie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        tvId = findViewById(R.id.tvId);
        tvActors = findViewById(R.id.tvActors);
        tvTitle = findViewById(R.id.tvTitle);
        imgMovie = findViewById(R.id.imgMovie);
        int id = getIntent().getIntExtra("id", 0);
        callApiGetMovieById(id);
    }

    private void callApiGetMovieById(int id) {
        ApiService.apiService.getMoviebyId(id).enqueue(new Callback<ApiResponse<Movie>>() {
            @Override
            public void onResponse(Call<ApiResponse<Movie>> call, Response<ApiResponse<Movie>> response) {
                Movie movie = response.body().getData();
                tvId.setText(movie.getId().toString());
                tvTitle.setText(movie.getTitle());
                tvActors.setText(movie.getActors());
                Picasso.get().load(movie.getImage()).into(imgMovie);
//                new AlertDialog.Builder(MoieDetailActivity.this).setMessage().show();
            }

            @Override
            public void onFailure(Call<ApiResponse<Movie>> call, Throwable throwable) {
                new AlertDialog.Builder(MovieDetailActivity.this).setMessage(throwable.getMessage()).show();
            }
        });
    }
}
