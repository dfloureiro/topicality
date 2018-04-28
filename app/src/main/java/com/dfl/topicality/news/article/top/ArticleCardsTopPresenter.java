package com.dfl.topicality.news.article.top;

import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.news.article.ArticleCardsPresenter;
import com.dfl.topicality.news.article.ArticleCardsPresenterInterface;
import com.dfl.topicality.news.article.ArticleCardsState;

import dfl.com.newsapikotin.NewsApi;
import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;
import io.reactivex.disposables.CompositeDisposable;

public class ArticleCardsTopPresenter extends ArticleCardsPresenter implements ArticleCardsPresenterInterface {

    private final ArticleCardsTopFragment articleCardsTopFragment;
    private final NewsApi requestFactory;
    private final Category category;
    private final Country country;
    private final String q;
    private int page;

    public ArticleCardsTopPresenter(ArticleCardsTopFragment view, NewsApi requestFactory, DatabaseInteractor databaseInteractor, Category category, Country country, String q, int page, CompositeDisposable compositeDisposable) {
        super(view, databaseInteractor, compositeDisposable);
        this.articleCardsTopFragment = view;
        this.requestFactory = requestFactory;
        this.category = category;
        this.country = country;
        this.q = q;
        this.page = page;
    }

    @Override
    public void subscribe(ArticleCardsState articleCardsState) {
        if (articleCardsState != null && !articleCardsState.getRemainingArticles().isEmpty()) {
            this.page = articleCardsState.getPage();
            articleCardsTopFragment.hideProgressbar();
            showArticles(articleCardsState.getRemainingArticles());
        } else {
            getTopHeadlineArticles();
        }
    }

    @Override
    public ArticleCardsState getState() {
        return new ArticleCardsState(page);
    }

    @Override
    public void getNextArticles() {
        page++;
        getTopHeadlineArticles();
    }

    @Override
    public void refresh() {
        page = 1;
        getTopHeadlineArticles();
    }

    public void getTopHeadlineArticles() {
        getArticles(requestFactory.getTopHeadlines(category, country, q, PAGE_SIZE, page));
    }
}
