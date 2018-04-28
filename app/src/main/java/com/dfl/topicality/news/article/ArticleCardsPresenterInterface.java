package com.dfl.topicality.news.article;

import com.dfl.topicality.database.DatabaseArticle;

public interface ArticleCardsPresenterInterface {

    void subscribe(ArticleCardsState articleCardsState);

    ArticleCardsState getState();

    void getNextArticles();

    void refresh();

    void upsertFavoriteSourceClicks(String sourceDomain);

    void upsertFavoriteSourceSaved(String sourceDomain);

    void setArticleAsClicked(DatabaseArticle databaseArticle);

    void setArticleAsViewed(DatabaseArticle databaseArticle);

    void saveArticle(DatabaseArticle databaseArticle);

    void unsubscribe();
}
