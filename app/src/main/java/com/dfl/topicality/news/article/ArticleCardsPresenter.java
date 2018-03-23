package com.dfl.topicality.news.article;

import com.dfl.topicality.database.DatabaseArticle;
import com.dfl.topicality.database.DatabaseInteractor;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dfl.com.newsapikotin.NewsApi;
import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;
import dfl.com.newsapikotin.enums.Language;
import dfl.com.newsapikotin.enums.SortBy;
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
    private final Language language;
    private final String q;

    private String domains;
    private int page;
    private final CompositeDisposable compositeDisposable;

    ArticleCardsPresenter(ArticleCardsFragment view, NewsApi requestFactory, DatabaseInteractor databaseInteractor, Category category, Country country, Language language, String q) {
        this.view = view;
        this.requestFactory = requestFactory;
        this.databaseInteractor = databaseInteractor;
        this.category = category;
        this.country = country;
        this.language = language;
        this.q = q;

        page = 1;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(ArticleCardsContract.State state) {
        if (state != null) {
            this.page = state.getPage();
            this.domains = state.getDomains();
            if (state.getRemainingArticles() != null && !state.getRemainingArticles().isEmpty()) {
                view.addArticles(state.getRemainingArticles());
                return;
            }
        }
        getArticles();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }


    @Override
    public ArticleCardsContract.State getState() {
        return new ArticleCardsState(page, domains, view.extractRemainingArticles());
    }

    @Override
    public void setArticleAsClicked(DatabaseArticle databaseArticle) {
        databaseArticle.setIsViewed(1);
        databaseArticle.setIsClicked(1);
        saveArticleToDatabase(databaseArticle);
    }

    @Override
    public void setArticleAsViewed(DatabaseArticle databaseArticle) {
        databaseArticle.setIsViewed(1);
        saveArticleToDatabase(databaseArticle);
    }

    @Override
    public void saveArticle(DatabaseArticle databaseArticle) {
        databaseArticle.setIsViewed(1);
        databaseArticle.setIsFavourite(1);
        saveArticleToDatabase(databaseArticle);
    }

    private void saveArticleToDatabase(DatabaseArticle article) {
        compositeDisposable.add(databaseInteractor.insertAllDatabaseArticles(article)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        throwable -> view.showSnackBar(throwable.getMessage())));
    }

    @Override
    public void getArticles() {
        if (view.getTypeOfNews().equals(NewsType.TOP)) {
            getTopHeadlineArticles();
        } else {
            getEverythingArticles();
        }
    }

    @Override
    public void upsertFavoriteSourceClicks(String sourceDomain) {
        compositeDisposable.add(databaseInteractor.upsertFavoriteSourcesClicks(sourceDomain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        throwable -> view.showSnackBar(throwable.getMessage())));
    }

    @Override
    public void upsertFavoriteSourceSaved(String sourceDomain) {
        compositeDisposable.add(databaseInteractor.upsertFavoriteSourcesSaved(sourceDomain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        throwable -> view.showSnackBar(throwable.getMessage())));
    }

    private void getEverythingArticles() {
        if (domains == null || domains.isEmpty()) {
            compositeDisposable.add(databaseInteractor.getAllFavoriteSourcesOrderByInteractionsDes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(favoriteSources -> {
                                StringBuilder domainsBuilder = new StringBuilder();
                                for (int i = 0; i < favoriteSources.size() && i < 20; i++) {
                                    domainsBuilder.append(favoriteSources.get(i).getSourcedomain()).append(",");
                                }
                                domains = domainsBuilder.toString();
                                getEverythingArticlesWithDomains();
                            },
                            throwable -> view.showSnackBar(throwable.getMessage())));
        } else {
            getEverythingArticlesWithDomains();
        }
    }

    private void checkIfArticlesAreViewed(List<DatabaseArticle> articles) {
        List<String> urlsList = new ArrayList<>();
        for (DatabaseArticle article : articles) {
            urlsList.add(article.getUrl());
        }
        compositeDisposable.add(databaseInteractor.getDatabaseArticleFromUrl(urlsList)
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
                            view.addArticles(articles);
                            page++;
                        },
                        throwable -> view.showSnackBar(throwable.getMessage())));
    }

    private void getEverythingArticlesWithDomains() {
        compositeDisposable.add(requestFactory.getEverything(q, null, domains, null, null, language, SortBy.PUBLISHEDAT, 20, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articles -> articles.getArticles() != null && !articles.getArticles().isEmpty())
                .map(articles -> ArticleMapper.mapArticlesToDatabaseArticles(articles.getArticles()))
                .subscribe(this::checkIfArticlesAreViewed,
                        this::errorHandling));
    }

    private void getTopHeadlineArticles() {
        compositeDisposable.add(requestFactory.getTopHeadlines(category, country, q, PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articles -> articles.getArticles() != null && !articles.getArticles().isEmpty())
                .map(articles -> ArticleMapper.mapArticlesToDatabaseArticles(articles.getArticles()))
                .subscribe(this::checkIfArticlesAreViewed,
                        this::errorHandling));
    }

    private void errorHandling(Throwable error) {
        if (error instanceof UnknownHostException || error instanceof ConnectException || error instanceof SocketTimeoutException) {
            view.showNetworkError();
        } else {
            view.showUnknownError();
        }
    }
}
