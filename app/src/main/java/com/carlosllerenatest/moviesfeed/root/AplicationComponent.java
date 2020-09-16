package com.carlosllerenatest.moviesfeed.root;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AplicationComponent.class})
public interface AplicationComponent {

    void inject();
}
