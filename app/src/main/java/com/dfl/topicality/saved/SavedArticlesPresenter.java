package com.dfl.topicality.saved;

import android.util.Log;

import com.dfl.topicality.database.DatabaseInteractor;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loureiro on 31-01-2018.
 */

public class SavedArticlesPresenter implements SavedArticlesContract.Presenter {

    private SavedArticlesContract.View view;
    private DatabaseInteractor databaseInteractor;

    private ArrayList<String> databaseArticleIdsList;
    private final CompositeDisposable compositeDisposable;

    SavedArticlesPresenter(SavedArticlesContract.View view, DatabaseInteractor databaseInteractor) {
        this.view = view;
        this.databaseInteractor = databaseInteractor;

        databaseArticleIdsList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(SavedArticlesContract.State state) {
        getAllArticles();
    }


    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public SavedArticlesContract.State getState() {
        return null;
    }

    @Override
    public void getAllArticles() {
        compositeDisposable.add(databaseInteractor.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(databaseArticles -> databaseArticles)
                .filter(databaseArticle -> !databaseArticleIdsList.contains(databaseArticle.getUrl()))
                .subscribe(databaseArticle -> {
                            view.addArticle(databaseArticle);
                            databaseArticleIdsList.add(databaseArticle.getUrl());
                        },
                        throwable -> Log.e("error", throwable.getMessage())));
    }

    @Override
    public void deleteArticle(String url, int viewHolderPosition) {
        compositeDisposable.add(databaseInteractor.deleteWhereUrl(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            view.removeArticle(viewHolderPosition);
                            databaseArticleIdsList.remove(viewHolderPosition);
                        },
                        throwable -> Log.e("error", throwable.getMessage())));
    }
}
