package com.dfl.topicality.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class Presenter {

    private final CompositeDisposable compositeDisposable;

    public Presenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
