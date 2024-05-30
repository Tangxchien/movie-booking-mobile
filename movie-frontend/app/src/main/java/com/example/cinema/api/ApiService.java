package com.example.cinema.api;

import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.BookTicket;
import com.example.cinema.model.Movie;
import com.example.cinema.model.ShowTime;
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
    @GET("api/get-showtime/{id}")
    Call<ApiResponse<List<ShowTime>>> getShowTimebyId(@Path("id") int id);
    @POST("api/login")
    Call<ApiResponse> loginUsers(@Body SignIn signIn);
    @POST("api/book-ticket")
    Call<ApiResponse> bookTicket(@Body BookTicket bookTicket);
    @GET("api/all-movies")
    Call<ApiResponse<List<Movie>>> getListMovie();
    @GET("/api/get-seats/{movieId}/{showtimeId}")
    Call<ApiResponse<List<String>>> getSeats(@Path("movieId") String movieId, @Path("showtimeId") String showtimeId);
}
