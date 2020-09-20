package com.carlosllerenatest.moviesfeed.root;

import android.app.Application;
import android.graphics.Movie;
import android.media.AsyncPlayer;

import com.carlosllerenatest.moviesfeed.http.MovieExtraInfoApiModule;
import com.carlosllerenatest.moviesfeed.http.MovieTitleApiModule;
import com.carlosllerenatest.moviesfeed.movies.MoviesModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                 .applicationModule(new ApplicationModule(this))
                 .moviesModule(new MoviesModule())
                 .movieTitleApiModule(new MovieTitleApiModule())
                 .movieExtraInfoApiModule(new MovieExtraInfoApiModule())
                 .build();

    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
