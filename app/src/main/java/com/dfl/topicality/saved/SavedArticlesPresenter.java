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

    private final SavedArticlesContract.View view;
    private final DatabaseInteractor databaseInteractor;

    private final ArrayList<String> databaseArticleIdsList;
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
        compositeDisposable.add(databaseInteractor.getAllDatabaseArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(databaseArticles -> databaseArticles)
                .filter(databaseArticle -> !databaseArticleIdsList.contains(databaseArticle.getUrl()))
                .subscribe(databaseArticle -> {
                            view.addArticle(databaseArticle);
                            databaseArticleIdsList.add(databaseArticle.getUrl());
                        },
                        throwable -> view.showSnackBar(throwable.getMessage())));
    }

    @Override
    public void deleteArticle(String url, int viewHolderPosition) {
        compositeDisposable.add(databaseInteractor.deleteDatabaseArticleWhereUrl(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            view.removeArticle(viewHolderPosition);
                            databaseArticleIdsList.remove(viewHolderPosition);
                        },
                        throwable -> view.showSnackBar(throwable.getMessage())));
    }

    @Override
    public void upsertFavoriteSourceClicks(String sourceDomain) {
        compositeDisposable.add(databaseInteractor.upsertFavoriteSourcesClicks(sourceDomain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {},
                        throwable -> view.showSnackBar(throwable.getMessage())));
    }
}
