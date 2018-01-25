package com.xavisson.marvelpractice.module;

import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javidelpalacio on 25/1/18.
 */

@Module(includes = ContextModule.class)
public class PicassoModule {

    @Provides
    public Picasso picasso(Context context){
        return new Picasso.Builder(context).build();
    }
}
