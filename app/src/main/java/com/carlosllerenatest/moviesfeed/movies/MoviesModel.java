package com.carlosllerenatest.moviesfeed.movies;

import android.graphics.Movie;

import com.carlosllerenatest.moviesfeed.http.apimodel.Result;
import com.carlosllerenatest.moviesfeed.http.apimodel.TopMovieRated;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;

public class MoviesModel implements  MoviesMVP.Model {

    private Repository repository;

    public MoviesModel(Repository repository){
        this.repository = repository;
    }
    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(repository.getResultData(), repository.getCountryData(), new BiFunction<Result, String, ViewModel>() {
            @Override
            public ViewModel apply(Result result, String country) {
                return new ViewModel(result.getTitle(),country);
            }
        });
    }
}
