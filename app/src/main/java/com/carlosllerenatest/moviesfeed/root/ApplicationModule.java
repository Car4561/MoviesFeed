package com.carlosllerenatest.moviesfeed.root;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application aplication;

    public ApplicationModule(Application aplication){
        this.aplication = aplication;

    }

    @Provides
    @Singleton
    public Context privaderContext(){
        return aplication;
    }

}
