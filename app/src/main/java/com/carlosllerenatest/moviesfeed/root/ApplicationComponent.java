package com.carlosllerenatest.moviesfeed.root;

import com.carlosllerenatest.moviesfeed.MainActivity;
import com.carlosllerenatest.moviesfeed.http.MovieExtraInfoApiModule;
import com.carlosllerenatest.moviesfeed.http.MovieTitleApiModule;
import com.carlosllerenatest.moviesfeed.http.MoviesExtraInfoApisService;
import com.carlosllerenatest.moviesfeed.movies.MoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MoviesModule.class,MovieTitleApiModule.class, MovieExtraInfoApiModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);


}
