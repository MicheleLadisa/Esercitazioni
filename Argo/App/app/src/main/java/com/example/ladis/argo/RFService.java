package com.example.ladis.argo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface RFService {

    String BASE_URL = "http://192.168.1.2:8080/ ";

    @GET("Eventi")
    Call<Eventi> getEventi();

    @POST("Login")
    Call<String> login(@Body User user);

    @POST("Singup")
    Call<String> singup(@Body User user);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}