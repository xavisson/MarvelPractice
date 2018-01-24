package com.xavisson.marvelpractice.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javidelpalacio on 24/1/18.
 */

public class RetrofitInstance {

    private static Retrofit retrofit;
    public static final String COMICS_BASE_URL = "https://gateway.marvel.com/v1/public/characters/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(COMICS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
