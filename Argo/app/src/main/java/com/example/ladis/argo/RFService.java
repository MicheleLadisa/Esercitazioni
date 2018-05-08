package com.example.ladis.argo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

interface RFService {

    String BASE_URL = "http://192.168.10.106:8080/Progetto/";

    @GET("Utenti")
    Call<PojoEvent> getPojo(); //get the PojoEvent object

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}