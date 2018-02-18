package com.dfl.topicality.news.article;

import android.util.Log;

import com.dfl.topicality.database.DatabaseArticle;
import com.dfl.topicality.database.DatabaseInteractor;

import dfl.com.newsapikotin.NewsApi;
import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsPresenter implements ArticleCardsContract.Presenter {

    private final static int PAGE_SIZE = 20;

    private final ArticleCardsContract.View view;
    private final NewsApi requestFactory;
    private final DatabaseInteractor databaseInteractor;
    private final Category category;
    private final Country country;
    private final String q;

    private int page;
    private final CompositeDisposable compositeDisposable;

    ArticleCardsPresenter(ArticleCardsFragment view, NewsApi requestFactory, DatabaseInteractor databaseInteractor, Category category, Country country, String q) {
        this.view = view;
        this.requestFactory = requestFactory;
        this.databaseInteractor = databaseInteractor;
        this.category = category;
        this.country = country;
        this.q = q;

        page = 1;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(ArticleCardsContract.State state) {
        if (state != null) {
            this.page = state.getPage();
            if (state.getRemainingArticles() != null && !state.getRemainingArticles().isEmpty()) {
                view.addArticles(state.getRemainingArticles());
                return;
            }
        }
        getTopHeadlineArticles();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }


    @Override
    public ArticleCardsContract.State getState() {
        return new ArticleCardsState(page, view.extractRemainingArticles());
    }

    @Override
    public void saveArticle(DatabaseArticle article) {
        compositeDisposable.add(databaseInteractor.insertAllDatabaseArticles(article)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d("done", "article inserted"),
                        throwable -> Log.e("error", throwable.getMessage())));
    }

    @Override
    public void getTopHeadlineArticles() {
        compositeDisposable.add(requestFactory.getTopHeadlines(category, country, q, PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(articles -> ArticleMapper.mapArticlesToDatabaseArticles(articles.getArticles()))
                .subscribe(articles -> {
                            view.addArticles(articles);
                            page++;
                        },
                        error -> Log.e("ERROR", "my error: " + error.getMessage())));
    }

    @Override
    public void upsertFavoriteSourceClicks(String sourceDomain) {
        compositeDisposable.add(databaseInteractor.upsertFavoriteSources(sourceDomain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d("done", "favorite source updated"),
                        throwable -> Log.e("error", throwable.getMessage())));
    }
}
