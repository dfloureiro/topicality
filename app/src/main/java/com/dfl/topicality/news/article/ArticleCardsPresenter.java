package com.dfl.topicality.news.article;

import android.util.Log;

import com.dfl.topicality.database.AppDatabase;
import com.dfl.topicality.database.DatabaseArticle;
import com.dfl.topicality.datamodel.Article;
import com.dfl.topicality.network.RequestFactory;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsPresenter implements ArticleCardsContract.Presenter {

    private final static int PAGE_SIZE = 20;

    private final ArticleCardsContract.View view;
    private final RequestFactory requestFactory;
    private AppDatabase appDatabase;
    private final String category;
    private final String country;
    private final String sources;
    private final String q;

    private int page;
    private final CompositeDisposable compositeDisposable;

    ArticleCardsPresenter(ArticleCardsFragment view, RequestFactory requestFactory, AppDatabase appDatabase, String category, String country, String sources, String q) {
        this.view = view;
        this.requestFactory = requestFactory;
        this.appDatabase = appDatabase;
        this.category = category;
        this.country = country;
        this.sources = sources;
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
    public void getTopHeadlineArticles() {
        compositeDisposable.add(requestFactory.getTopHeadlines(category, country, sources, q, PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articlesResponse -> {
                            view.addArticles(articlesResponse.getArticles());
                            page += 1;
                        },
                        error -> Log.e("ERROR", "my error: " + error.getMessage())));
    }

    @Override
    public void saveArticle(Article article) {
        DatabaseArticle databaseArticle = new DatabaseArticle(article.getUrl(), article.getSource().getName(),
                article.getAuthor(), article.getTitle(), article.getDescription(), article.getUrlToImage(), article.getPublishedAt());
        compositeDisposable.add(Completable.fromAction(() ->
                appDatabase.databaseArticleDao().insertAll(databaseArticle))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d("done", "article inserted"),
                        throwable -> Log.e("error", throwable.getMessage())));
    }
}
