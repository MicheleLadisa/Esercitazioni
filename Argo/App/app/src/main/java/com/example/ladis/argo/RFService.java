package com.example.ladis.argo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

interface RFService {

    String BASE_URL = "http://192.168.43.156:8080/ ";

    @GET("Eventi")
    Call<Evento[]> getEventi();

    @POST("Login")
    Call<String> login(@Body User user);

    @POST("Singup")
    Call<String> singup(@Body User user);

    @DELETE("DeleteAccount/{name}")
    Call<String> deleteAccount(@Path("name") String name);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}