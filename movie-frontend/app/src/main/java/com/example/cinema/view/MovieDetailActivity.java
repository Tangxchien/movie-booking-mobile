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

import java.text.DateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvTitle, tvActorsV, tvDescriptionV, tvAgeLimitV, tvReleaseDateV, tvDirectorV;
    ImageView imgMovie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        tvDescriptionV = findViewById(R.id.tvDescriptionV);
        tvActorsV = findViewById(R.id.tvActorsV);
        tvTitle = findViewById(R.id.tvTitle);
        tvAgeLimitV = findViewById(R.id.tvAgeLimitV);
        tvReleaseDateV = findViewById(R.id.tvReleaseDateV);
        tvDirectorV = findViewById(R.id.tvDirectorV);
        imgMovie = findViewById(R.id.imgMovie);
        int id = getIntent().getIntExtra("id", 0);
        callApiGetMovieById(id);
    }

    private void callApiGetMovieById(int id) {
        ApiService.apiService.getMoviebyId(id).enqueue(new Callback<ApiResponse<Movie>>() {
            @Override
            public void onResponse(Call<ApiResponse<Movie>> call, Response<ApiResponse<Movie>> response) {
                Movie movie = response.body().getData();
                tvDescriptionV.setText(movie.getDescription());
                tvTitle.setText(movie.getTitle().toUpperCase());
                tvActorsV.setText(movie.getActors());
                tvAgeLimitV.setText("Phim dành cho khán giả trên " + String.valueOf(movie.getAgeLimit()) + " tuổi");
                tvDirectorV.setText(movie.getDirector());
                Date releaseDate = movie.getReleaseDate();
                tvReleaseDateV.setText(releaseDate.getDay() + " tháng " + releaseDate.getMonth() + ", " + (releaseDate.getYear() + 1900));
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
