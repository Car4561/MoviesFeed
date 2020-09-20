package com.carlosllerenatest.moviesfeed.movies;

import com.carlosllerenatest.moviesfeed.http.MoviesApiService;
import com.carlosllerenatest.moviesfeed.http.MoviesExtraInfoApisService;
import com.carlosllerenatest.moviesfeed.http.apimodel.OmdbApi;
import com.carlosllerenatest.moviesfeed.http.apimodel.Result;
import com.carlosllerenatest.moviesfeed.http.apimodel.TopMovieRated;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

public class MoviesRepository implements Repository {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApisService moviesExtraInfoApisService;

    private List<String> countries;
    private List<Result> results;
    private long lasttimestamp ;

    private static final long CACHE_LIFETIME = 20 * 1000;


    public MoviesRepository(MoviesApiService moviesApiService, MoviesExtraInfoApisService moviesExtraInfoApisService){
        this.moviesApiService = moviesApiService;
        this.moviesExtraInfoApisService = moviesExtraInfoApisService;

        this.lasttimestamp = System.currentTimeMillis();

        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public boolean isUpdate(){
        return (System.currentTimeMillis() - lasttimestamp)  < CACHE_LIFETIME;
    }

    @Override
    public Observable<Result> getResultFromNetwork() {

        Observable<TopMovieRated> topMovieRatedObservable = moviesApiService.getTopRated(1)
                .concatWith(moviesApiService.getTopRated(2))
                .concatWith(moviesApiService.getTopRated(3));

        return topMovieRatedObservable
                            .concatMap(new Function<TopMovieRated, ObservableSource<Result>>() {
                                    @Override
                                    public ObservableSource<Result > apply(TopMovieRated topMovieRated){
                                         return Observable.fromIterable(topMovieRated.getResults());
                            }})
                            .doOnNext(new Consumer<Result>() {
                                    @Override
                                    public void accept(Result result1) throws Throwable {
                                        results.add(result1);
                                    }
                              });
    }

    @Override
    public Observable<Result> getResultFromCache() {
        if(isUpdate()){
            return Observable.fromIterable(results);
        }else {
            lasttimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork());
    }

    @Override
    public Observable<String> getCountryFromNetwork() {

  /*      for (Result result: results) {
            Observable<OmdbApi> topMovieRatedObservable = moviesExtraInfoApisService.getExtraInfoMovie(result.getTitle())
                                                        .doOnNext(new Consumer<OmdbApi>() {
                                                            @Override
                                                            public void accept(OmdbApi omdbApi) throws Throwable {
                                                                countries.add(omdbApi.getCountry());
                                                            }
                                                        });

        }*/
      /*  getResultData().doOnNext(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Throwable {
                moviesExtraInfoApisService.getExtraInfoMovie(result.getTitle());
            }
        });  */
      return getResultData().concatMap(new Function<Result, ObservableSource<OmdbApi>>() {
            @Override
            public ObservableSource<OmdbApi> apply(final Result result)  {
                Observable<OmdbApi> omdbApiObservable = moviesExtraInfoApisService.getExtraInfoMovie(result.getTitle());
                return omdbApiObservable.concatMap(new Function<OmdbApi, ObservableSource<OmdbApi>>() {
                    @Override
                    public ObservableSource<OmdbApi> apply(OmdbApi omdbApi)  {
                        if (omdbApi.getCountry() == null) {
                            return moviesExtraInfoApisService.getExtraInfoMovie(result.getOriginalTitle());
                        } else {
                            return  Observable.just(omdbApi);
                        }
                    }
                });
            }
        }).concatMap(new Function<OmdbApi, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(OmdbApi omdbApi) throws Throwable {
                return Observable.just(omdbApi.getCountry());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String country)  {
                countries.add(country);
            }
        });

    }

    @Override
    public Observable<String> getCountryFromCache() {
        if(isUpdate()){
            return Observable.fromIterable(countries);
        }else {
            lasttimestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork());
    }
}
