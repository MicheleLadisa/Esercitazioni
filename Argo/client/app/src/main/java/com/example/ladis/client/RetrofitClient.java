package com.example.ladis.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ladis on 17/05/2018.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    private static String URL = "http://192.168.1.7:8080/";
    public static Retrofit GetClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
