package com.example.cinema.api;

import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.BookTicket;
import com.example.cinema.model.Movie;
import com.example.cinema.model.ShowTime;
import com.example.cinema.model.ForgotPasswordRequest;
import com.example.cinema.model.Register;
import com.example.cinema.model.SignIn;
import com.example.cinema.model.SignInReponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.9.4.20:8080/")
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
    @POST("api/register")
    Call<ApiResponse> registerUsers(@Body Register register);
    @PUT("api/update-account/{id}")
    Call<ApiResponse> editUsers(@Path("id") int id, @Body Register register);
    @PUT("api/change-password/{accountId}/{oldPassword}/{newPassword}")
    Call<ApiResponse> changePassword(@Path("accountId") int accountId, @Path("oldPassword") String oldPassword, @Path("newPassword") String newPassword);
    @GET("api/get-tickets-by-account/{accountId}")
    Call<ApiResponse> getTicketByAccountId(@Path("accountId") int accountId);
    @POST("api/forget-password")
    Call<ApiResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);
}
