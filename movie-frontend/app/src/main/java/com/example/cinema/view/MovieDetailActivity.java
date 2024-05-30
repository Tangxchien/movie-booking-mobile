package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Movie;
import com.example.cinema.model.ShowTime;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvTitle, tvActorsV, tvDescriptionV, tvAgeLimitV, tvReleaseDateV, tvDirectorV, tvGenres;
    ImageView imgMovie;
    Button btnBookTicket;
    Spinner spShowTimes;
    Intent intent;
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
        tvGenres = findViewById(R.id.tvGenres);
        imgMovie = findViewById(R.id.imgMovie);
        spShowTimes = findViewById(R.id.spShowTimes);
        btnBookTicket = findViewById(R.id.btnBookTicket);
        int id = getIntent().getIntExtra("id", 0);
        callApiGetMovieById(id);
        callApiGetShowTimes(id);
    }

    private void callApiGetMovieById(int id) {
        ApiService.apiService.getMoviebyId(id).enqueue(new Callback<ApiResponse<Movie>>() {
            @Override
            public void onResponse(Call<ApiResponse<Movie>> call, Response<ApiResponse<Movie>> response) {
                Movie movie = response.body().getData();
                tvDescriptionV.setText(movie.getDescription());
                tvTitle.setText(movie.getTitle().toUpperCase());
                tvActorsV.setText(movie.getActors());

                String ageLimit = String.valueOf(movie.getAgeLimit());
                if(ageLimit.equals("0")){
                    tvAgeLimitV.setText("Phim không giới hạn độ tuổi");
                }
                else
                tvAgeLimitV.setText("Phim dành cho khán giả trên " + ageLimit + " tuổi");
                tvDirectorV.setText(movie.getDirector());

                List<String> genres = movie.getGenres();
                StringBuilder genresTextBuilder = new StringBuilder();
                for (int i = 0; i < genres.size(); i++) {
                    genresTextBuilder.append(genres.get(i));
                    if (i < genres.size() - 1) {
                        genresTextBuilder.append(", ");
                    }
                }
                String genresText = genresTextBuilder.toString();
                tvGenres.setText(genresText);
                Date releaseDate = movie.getReleaseDate();
                tvReleaseDateV.setText(releaseDate.getDay() + " tháng " + releaseDate.getMonth() + ", " + (releaseDate.getYear() + 1900));
                Picasso.get().load(movie.getImage()).into(imgMovie);
                spShowTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedShowTime = (String) parent.getItemAtPosition(position);
                        intent = new Intent(MovieDetailActivity.this, BookTicketActivity.class);
                        intent.putExtra("showTimeId", selectedShowTime.charAt(0));
                        intent.putExtra("showTime", selectedShowTime.substring(4));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                btnBookTicket.setOnClickListener(v -> {
                    intent.putExtra("price", movie.getPrice());
                    intent.putExtra("name", movie.getTitle());
                    intent.putExtra("MovieName", movie.getTitle());
                    startActivity(intent);
                });

            }

            @Override
            public void onFailure(Call<ApiResponse<Movie>> call, Throwable throwable) {
                new AlertDialog.Builder(MovieDetailActivity.this).setMessage(throwable.getMessage()).show();
            }
        });
    }
    private void callApiGetShowTimes(int movieId) {
        ApiService.apiService.getShowTimebyId(movieId).enqueue(new Callback<ApiResponse<List<ShowTime>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ShowTime>>> call, Response<ApiResponse<List<ShowTime>>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    List<ShowTime> showTimes = response.body().getData();
                    List<String> showTimeStrings = new ArrayList<>();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd 'thg' MM, yyyy", Locale.getDefault());
                    for (ShowTime showTime : showTimes) {
                        String formattedDate = sdf.format(showTime.getShowTime());
                        showTimeStrings.add(showTime.getId() + " : " + formattedDate);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MovieDetailActivity.this, android.R.layout.simple_spinner_item, showTimeStrings);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spShowTimes.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ShowTime>>> call, Throwable throwable) {
                new AlertDialog.Builder(MovieDetailActivity.this).setMessage(throwable.getMessage()).show();
            }
        });
    }


}
