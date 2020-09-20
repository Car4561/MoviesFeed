package com.carlosllerenatest.moviesfeed.movies;

import org.reactivestreams.Subscription;

import java.util.Observable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviesPresenter implements MoviesMVP.Presenter {

    private MoviesMVP.Model model;
    private MoviesMVP.View  view;

    private Disposable subscription = null;

    public MoviesPresenter(MoviesMVP.Model model){
            this.model = model;
    }
    @Override
    public void loadData() {
        subscription = model.result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ViewModel>() {
                    @Override
                    public void onNext(ViewModel viewModel) {
                        if(view != null){
                            view.updateData(viewModel);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        view.showSnackbar("Error al descargar las peliculas...");
                    }

                    @Override
                    public void onComplete() {
                        view.showSnackbar("Información descargada con éxito");
                    }

                });
    }

    @Override
    public void rxJavaUnsuscribe() {
        if(subscription != null &&!subscription.isDisposed()){
            subscription.dispose();
        }
    }

    @Override
    public void setView(MoviesMVP.View view) {
        this.view = view;
    }
}
