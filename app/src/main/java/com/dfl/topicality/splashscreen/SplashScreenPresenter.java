package com.dfl.topicality.splashscreen;

import android.util.Log;

import com.dfl.topicality.DomainUtils;
import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.database.FavoriteSource;

import java.util.ArrayList;
import java.util.List;

import dfl.com.newsapikotin.Model;
import dfl.com.newsapikotin.NewsApi;
import dfl.com.newsapikotin.enums.Country;
import dfl.com.newsapikotin.enums.Language;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loureiro on 18-02-2018.
 */

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private final SplashScreenContract.View view;
    private final DatabaseInteractor databaseInteractor;
    private final NewsApi requestFactory;
    private final Country country;
    private final Language language;

    private final CompositeDisposable compositeDisposable;

    SplashScreenPresenter(SplashScreenContract.View view, NewsApi requestFactory, DatabaseInteractor databaseInteractor, Country country, Language language) {
        this.view = view;
        this.requestFactory = requestFactory;
        this.databaseInteractor = databaseInteractor;
        this.country = country;
        this.language = language;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(SplashScreenContract.State state) {
        getTopSourcesByCountryAndLanguage();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public SplashScreenContract.State getState() {
        return null;
    }

    private void getTopSourcesByCountryAndLanguage() {
        compositeDisposable.add(
                Flowable.zip(
                        requestFactory.getTopHeadlines(null, country, null, 20, 1)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()),
                        requestFactory.getSources(null, language, country)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()),
                        (articles, sources) -> {
                            List<FavoriteSource> favoriteSources = new ArrayList<>();
                            for (Model.Article article : articles.getArticles()) {
                                favoriteSources.add(new FavoriteSource(DomainUtils.getDomainName(article.getUrl()), 0, 0));
                            }
                            for (Model.Source source : sources.getSources()) {
                                favoriteSources.add(new FavoriteSource(DomainUtils.getDomainName(source.getUrl()), 0, 0));
                            }

                            return favoriteSources;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(favoriteSources -> addAllFavoriteSources(favoriteSources.toArray(new FavoriteSource[favoriteSources.size()])),
                                throwable -> Log.e("error", throwable.getMessage())
                        ));
    }

    private void addAllFavoriteSources(FavoriteSource[] sources) {
        compositeDisposable.add(databaseInteractor.insertAllFavoriteSources(sources)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.finishSplash(),
                        throwable -> Log.e("error", throwable.getMessage())));
    }
}
