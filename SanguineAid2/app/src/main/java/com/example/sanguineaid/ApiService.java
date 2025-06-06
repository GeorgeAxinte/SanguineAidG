package com.example.sanguineaid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;import retrofit2.http.Path;

public interface ApiService {
    @POST("/register")
    Call<User> registerUser(@Body User user);
    @POST("/login")
    Call<ResponseBody> loginUser(@Body UserLogin userLogin);
    @POST("/add_points/{username}")
    Call<ResponseBody> addPoints(@Path("username") String username);
    @GET("/get_points/{username}")
    Call<ResponseBody> getPoints(@Path("username") String username);
    @GET("/get_firstname/{username}")
    Call<ResponseBody> get_firstname(@Path("username") String username);
}
