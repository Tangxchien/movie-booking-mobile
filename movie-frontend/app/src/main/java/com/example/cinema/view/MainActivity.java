package com.example.cinema.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinema.R;
import com.example.cinema.adapter.MovieAdapter;
import com.example.cinema.api.ApiService;
import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvMovies;
    private MovieAdapter movieAdapter;
    private Button btnBack;
    private List<Movie> mListMovie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBack = findViewById(R.id.btnBack);
        rcvMovies = findViewById(R.id.rcv_movies);
        mListMovie = new ArrayList<>();

        //Them dai phan cach bang DividerItemDecoration
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        rcvMovies.addItemDecoration(decoration);

        callApiGetMoives();
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void callApiGetMoives(){
        ApiService.apiService.getListMovie().enqueue(new Callback<ApiResponse<List<Movie>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Movie>>> call, Response<ApiResponse<List<Movie>>> response) {
                assert response.body() != null;
                mListMovie = response.body().getData();
                movieAdapter = new MovieAdapter(mListMovie, position -> {
                    Integer id = movieAdapter.getData(position).getId();
                    Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);

                });
                rcvMovies.setAdapter(movieAdapter);
//                new AlertDialog.Builder(MainActivity.this).setMessage(response.body().getData().toString()).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Movie>>> call, Throwable throwable) {
                throwable.printStackTrace();
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Error -> " + throwable.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
        
    }
}
