package com.xavisson.marvelpractice.net;

import com.xavisson.marvelpractice.BuildConfig;
import com.xavisson.marvelpractice.model.Comic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by javidelpalacio on 22/1/18.
 */

public interface MarvelApi {

    String characterId = BuildConfig.CHARACTER_ID;
    String COMICS_FROM_CHARACTER_ID = characterId + "/comics";
    String TIMESTAMP = "ts";
    String API_KEY = "apikey";
    String HASH = "hash";
    String ORDER = "orderBy";

    @GET(COMICS_FROM_CHARACTER_ID)
    Call<Comic> getComics(
            @Query(ORDER) String order,
            @Query(API_KEY) String publicKey,
            @Query(HASH) String hash,
            @Query(TIMESTAMP) String timestamp);
}
