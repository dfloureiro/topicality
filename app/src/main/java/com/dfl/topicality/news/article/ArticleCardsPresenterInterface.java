package com.dfl.topicality.news.article;

public interface ArticleCardsPresenterInterface {

    void subscribe(ArticleCardsState articleCardsState);

    ArticleCardsState getState();

    void getNextArticles();

    void refresh();

    void unsubscribe();
}
