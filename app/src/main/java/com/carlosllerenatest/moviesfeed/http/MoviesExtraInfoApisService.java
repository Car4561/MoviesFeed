package com.carlosllerenatest.moviesfeed.http;

import com.carlosllerenatest.moviesfeed.http.apimodel.OmdbApi;
import com.carlosllerenatest.moviesfeed.http.apimodel.TopMovieRated;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesExtraInfoApisService {

    @GET("/")
    Observable<OmdbApi> getExtraInfoMovie(@Query("t") String tittle);
}
