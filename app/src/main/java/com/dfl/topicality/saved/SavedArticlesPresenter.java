package com.dfl.topicality.saved;

import android.util.Log;

import com.dfl.topicality.database.AppDatabase;
import com.dfl.topicality.database.DatabaseArticle;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loureiro on 31-01-2018.
 */

public class SavedArticlesPresenter implements SavedArticlesContract.Presenter {

    private SavedArticlesContract.View view;
    private AppDatabase appDatabase;

    private ArrayList<String> databaseArticleIdsList;
    private final CompositeDisposable compositeDisposable;

    public SavedArticlesPresenter(SavedArticlesContract.View view, AppDatabase appDatabase){
        this.view = view;
        this.appDatabase = appDatabase;

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
        compositeDisposable.add(appDatabase.databaseArticleDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(databaseArticles -> {
                    for(DatabaseArticle databaseArticle : databaseArticles){
                        if(!databaseArticleIdsList.contains(databaseArticle.getUrl())){
                            view.addArticle(databaseArticle);
                            databaseArticleIdsList.add(databaseArticle.getUrl());
                        }
                    }
                        },
                        throwable -> Log.e("error", throwable.getMessage())));
    }

    @Override
    public void deleteArticle(String url, int viewHolderPosition){
        compositeDisposable.add(Completable.fromAction(() ->
                appDatabase.databaseArticleDao().deleteWhereUrl(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {view.removeArticle(viewHolderPosition);
                databaseArticleIdsList.remove(viewHolderPosition);
                        },
                        throwable -> Log.e("error", throwable.getMessage())));
    }
}
