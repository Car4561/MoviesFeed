package com.carlosllerenatest.moviesfeed.movies;


import com.carlosllerenatest.moviesfeed.http.apimodel.Result;
import com.carlosllerenatest.moviesfeed.http.apimodel.TopMovieRated;

import io.reactivex.rxjava3.core.Observable;

public interface Repository {

    Observable<Result> getResultFromNetwork();
    Observable<Result> getResultFromCache();
    Observable<Result> getResultData();

    Observable<String> getCountryFromNetwork();
    Observable<String> getCountryFromCache();
    Observable<String> getCountryData();

}
