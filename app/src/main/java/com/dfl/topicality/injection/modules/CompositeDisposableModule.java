package com.dfl.topicality.injection.modules;

import com.dfl.topicality.injection.scopes.PerFragment;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class CompositeDisposableModule {

    @PerFragment
    @Provides
    public CompositeDisposable compositeDisposable() {
        return new CompositeDisposable();
    }
}
