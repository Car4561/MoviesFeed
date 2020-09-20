package com.carlosllerenatest.moviesfeed.movies;

import com.carlosllerenatest.moviesfeed.http.MovieExtraInfoApiModule;
import com.carlosllerenatest.moviesfeed.http.MoviesApiService;
import com.carlosllerenatest.moviesfeed.http.MoviesExtraInfoApisService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    @Provides
    public MoviesMVP.Presenter providerMoviesPresenter(MoviesMVP.Model model){
        return new MoviesPresenter(model);
    }

    @Provides
    public MoviesMVP.Model providerMoviesModel(Repository repository){
        return new MoviesModel(repository);
    }

    @Singleton
    @Provides
    public Repository providerMoviesRepository(MoviesApiService moviesApiService, MoviesExtraInfoApisService moviesExtraInfoApisService){
       return new MoviesRepository(moviesApiService,moviesExtraInfoApisService);
    }

}
