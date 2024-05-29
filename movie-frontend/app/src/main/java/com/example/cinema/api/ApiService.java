package com.example.cinema.api;

import com.example.cinema.model.ApiResponse;
import com.example.cinema.model.Currency;
import com.example.cinema.model.ForgotPasswordRequest;
import com.example.cinema.model.Register;
import com.example.cinema.model.SignIn;
import com.example.cinema.model.SignInReponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
            .baseUrl("http://192.168.0.105:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/movies/{id}")
    Call<Currency> getMoviebyId(@Path("id") int id);
    @POST("api/login")
    Call<ApiResponse> loginUsers(@Body SignIn signIn);
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
