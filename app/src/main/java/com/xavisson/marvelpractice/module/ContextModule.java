package com.xavisson.marvelpractice.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javidelpalacio on 25/1/18.
 */

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    public Context context(){ return context.getApplicationContext(); }
}
