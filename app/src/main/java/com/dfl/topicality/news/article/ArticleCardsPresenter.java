package com.dfl.topicality.news.article;

import com.dfl.topicality.base.Presenter;
import com.dfl.topicality.database.DatabaseArticle;
import com.dfl.topicality.database.DatabaseInteractor;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import dfl.com.newsapikotin.Model;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsPresenter extends Presenter {

    public final static int PAGE_SIZE = 20;

    private final ArticleCardsFragment view;
    private final DatabaseInteractor databaseInteractor;

    public ArticleCardsPresenter(ArticleCardsFragment view, DatabaseInteractor databaseInteractor, CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
        this.view = view;
        this.databaseInteractor = databaseInteractor;
    }

    public void upsertFavoriteSourceClicks(String sourceDomain) {
        addDisposable(databaseInteractor.upsertFavoriteSourcesClicks(sourceDomain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        this::errorHandling));
    }

    public void upsertFavoriteSourceSaved(String sourceDomain) {
        addDisposable(databaseInteractor.upsertFavoriteSourcesSaved(sourceDomain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        this::errorHandling));
    }

    public void setArticleAsClicked(DatabaseArticle databaseArticle) {
        databaseArticle.setIsViewed(1);
        databaseArticle.setIsClicked(1);
        saveArticleToDatabase(databaseArticle);
    }

    public void setArticleAsViewed(DatabaseArticle databaseArticle) {
        databaseArticle.setIsViewed(1);
        saveArticleToDatabase(databaseArticle);
    }

    public void saveArticle(DatabaseArticle databaseArticle) {
        databaseArticle.setIsViewed(1);
        databaseArticle.setIsFavourite(1);
        saveArticleToDatabase(databaseArticle);
    }

    private void saveArticleToDatabase(DatabaseArticle article) {
        addDisposable(databaseInteractor.insertAllDatabaseArticles(article)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        this::errorHandling));
    }

    protected void getArticles(Flowable<Model.Articles> flowable) {
        addDisposable(flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articles -> articles.getArticles() != null && !articles.getArticles().isEmpty())
                .map(articles -> ArticleMapper.mapArticlesToDatabaseArticles(Objects.requireNonNull(articles.getArticles())))
                .subscribe(this::checkIfArticlesAreViewed,
                        this::errorHandling));
    }

    private void checkIfArticlesAreViewed(List<DatabaseArticle> articles) {
        List<String> urlsList = new ArrayList<>();
        for (DatabaseArticle article : articles) {
            urlsList.add(article.getUrl());
        }
        addDisposable(databaseInteractor.getDatabaseArticleFromUrl(urlsList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(databaseArticles -> {
                    for (DatabaseArticle databaseArticle : databaseArticles) {
                        for (int position = 0; position < articles.size(); position++) {
                            if (articles.get(position).getUrl().equals(databaseArticle.getUrl())) {
                                articles.get(position).setIsViewed(1);
                                articles.get(position).setIsClicked(databaseArticle.getIsClicked());
                                break;
                            }
                        }
                    }
                    Collections.sort(articles, (databaseArticle1, databaseArticle2) -> Integer.compare(databaseArticle1.getIsViewed(), databaseArticle2.getIsViewed()));
                    showArticles(articles);
                }, this::errorHandling));
    }

    protected void showArticles(List<DatabaseArticle> articles) {
        for (DatabaseArticle databaseArticle : articles) {
            view.addArticle(databaseArticle);
        }
    }

    protected void errorHandling(Throwable error) {
        // TODO: 15-04-2018 error codes handling https://newsapi.org/docs/errors
        if (error instanceof UnknownHostException || error instanceof ConnectException || error instanceof SocketTimeoutException) {
            view.showNetworkError();
        } else {
            view.showUnknownError();
        }
    }
}
