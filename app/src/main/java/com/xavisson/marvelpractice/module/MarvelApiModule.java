package com.xavisson.marvelpractice.module;

import com.xavisson.marvelpractice.net.MarvelApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javidelpalacio on 25/1/18.
 */

@Module
public class MarvelApiModule {

    @Provides
    public MarvelApi getMarvelApi(Retrofit retrofit) {
        return retrofit.create(MarvelApi.class);
    }

    @Provides
    public Retrofit retrofit(GsonConverterFactory gsonConverterFactory) {

        String COMICS_BASE_URL = "https://gateway.marvel.com/v1/public/characters/";

        return new retrofit2.Retrofit.Builder()
                .baseUrl(COMICS_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(){
        return GsonConverterFactory.create();
    }
}
