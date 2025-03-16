package com.example.sanguineaid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/register")
    Call<User> registerUser(@Body User user);
    @POST("/login")
    Call<ResponseBody> loginUser(@Body UserLogin userLogin);
}
