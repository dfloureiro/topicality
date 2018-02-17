package com.dfl.topicality.news.article;

import com.dfl.topicality.database.DatabaseArticle;

import java.util.ArrayList;
import java.util.List;

import dfl.com.newsapikotin.Model.Article;

/**
 * Created by loureiro on 17-02-2018.
 */

class ArticleMapper {

    static List<DatabaseArticle> mapArticlesToDatabaseArticles(List<Article> articles) {
        List<DatabaseArticle> databaseArticles = new ArrayList<>();
        for (Article article : articles) {
            databaseArticles.add(mapArticleToDatabaseArticle(article));
        }
        return databaseArticles;
    }

    private static DatabaseArticle mapArticleToDatabaseArticle(Article article) {
        return new DatabaseArticle(article.getUrl(), article.getSource().getName(),
                article.getAuthor(), article.getTitle(), article.getDescription(), article.getUrlToImage(), article.getPublishedAt());
    }
}
