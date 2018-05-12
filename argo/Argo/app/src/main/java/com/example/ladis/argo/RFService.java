package com.example.ladis.argo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

interface RFService {

    String BASE_URL = "http://192.168.10.109:8080/Progetto/";

    @GET("Utenti name=fdarfaga pass=fadfa")
    Call<PojoEvent> getPojoUtenti(); //get the PojoEvent object
    @GET("Eventi")
    Call<PojoEvent> getPojoEventi();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}