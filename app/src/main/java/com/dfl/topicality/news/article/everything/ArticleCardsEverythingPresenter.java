package com.dfl.topicality.news.article.everything;

import android.text.TextUtils;

import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.database.FavoriteSource;
import com.dfl.topicality.news.article.ArticleCardsPresenter;
import com.dfl.topicality.news.article.ArticleCardsPresenterInterface;
import com.dfl.topicality.news.article.ArticleCardsState;

import dfl.com.newsapikotin.NewsApi;
import dfl.com.newsapikotin.enums.Language;
import dfl.com.newsapikotin.enums.SortBy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ArticleCardsEverythingPresenter extends ArticleCardsPresenter implements ArticleCardsPresenterInterface {

    private final NewsApi requestFactory;
    private final DatabaseInteractor databaseInteractor;
    private final Language language;
    private final String q;
    private int page;

    public ArticleCardsEverythingPresenter(ArticleCardsEverythingFragment view, NewsApi requestFactory, DatabaseInteractor databaseInteractor, Language language, String q, int page, CompositeDisposable compositeDisposable) {
        super(view, databaseInteractor, compositeDisposable);
        this.requestFactory = requestFactory;
        this.databaseInteractor = databaseInteractor;
        this.language = language;
        this.q = q;
        this.page = page;
    }

    @Override
    public void subscribe(ArticleCardsState articleCardsState) {
        if (articleCardsState != null) {
            this.page = articleCardsState.getPage();
            showArticles(articleCardsState.getRemainingArticles());
        } else {
            getEverythingArticles();
        }
    }

    @Override
    public ArticleCardsState getState() {
        return new ArticleCardsState(page);
    }

    @Override
    public void getNextArticles() {
        page++;
        getEverythingArticles();
    }

    @Override
    public void refresh() {
        page = 1;
        getEverythingArticles();
    }

    public void getEverythingArticles() {
        addDisposable(databaseInteractor.getAllFavoriteSourcesOrderByInteractionsDes()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(favoriteSources -> favoriteSources)
                .map(FavoriteSource::getSourcedomain)
                .toList()
                .map(domains -> TextUtils.join(",", domains))
                .subscribe(domainsString ->
                                getArticles(requestFactory.getEverything(q, null, domainsString, null, null, language, SortBy.PUBLISHEDAT, PAGE_SIZE, page))
                        , this::errorHandling));
    }
}
