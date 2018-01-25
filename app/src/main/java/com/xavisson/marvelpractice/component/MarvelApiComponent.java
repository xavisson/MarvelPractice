package com.xavisson.marvelpractice.component;

import com.squareup.picasso.Picasso;
import com.xavisson.marvelpractice.module.MarvelApiModule;
import com.xavisson.marvelpractice.module.PicassoModule;
import com.xavisson.marvelpractice.net.MarvelApi;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by javidelpalacio on 24/1/18.
 */
@Component(modules = {MarvelApiModule.class, PicassoModule.class})
public interface MarvelApiComponent {

    MarvelApi getMarvelApi();
    Retrofit retrofit();
    Picasso getPicasso();
}
