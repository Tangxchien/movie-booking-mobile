package com.example.cinema.api;

import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Register;
import com.example.cinema.model.SignIn;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://172.20.10.8:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/movies/{id}")
    Call<ApiResponse<Movie>> getMoviebyId(@Path("id") int id);
    @POST("api/login")
    Call<ApiResponse> loginUsers(@Body SignIn signIn);
    @POST("api/register")
    Call<Register> registerUsers(@Body Register register);
    @GET("api/all-movies")
    Call<ApiResponse<List<Movie>>> getListMovie();
}
