package com.carlosllerenatest.moviesfeed.http;

import com.carlosllerenatest.moviesfeed.http.apimodel.TopMovieRated;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("top_rated")
    Observable<TopMovieRated> getTopRated(@Query("page") Integer page);
}
