package com.example.ladis.app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface RFService {

    @GET("Eventi")
    Call<Evento[]> getEventi();

    @POST("Login")
    Call<String> login(@Body User user);

    @POST("Singup")
    Call<String> singup(@Body User user);

    @DELETE("DeleteAccount/{name}")
    Call<String> deleteAccount(@Path("name") String name);

    @PUT("ChangePassword")
    Call<String> changePassword(@Body User user);

}