package com.dfl.topicality.news.article;

import com.dfl.topicality.datamodel.Article;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.Transient;

import java.util.List;

/**
 * Created by loureiro on 30-01-2018.
 */

@Parcel
public class ArticleCardsState implements ArticleCardsContract.State {

    @Transient
    static final String ARTICLE_CARDS_STATE = "ARTICLE_CARDS_STATE";

    int page;
    List<Article> articles;

    @ParcelConstructor
    ArticleCardsState(int page, List<Article> articles) {
        this.page = page;
        this.articles = articles;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public List<Article> getRemainingArticles() {
        return articles;
    }
}
