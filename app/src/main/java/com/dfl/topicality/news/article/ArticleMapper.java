package com.dfl.topicality.news.article;

import com.dfl.topicality.database.DatabaseArticle;

import java.util.ArrayList;
import java.util.List;

import dfl.com.newsapikotin.Model.Article;

/**
 * Created by loureiro on 17-02-2018.
 */

public class ArticleMapper {

    public static List<DatabaseArticle> mapArticlesToDatabaseArticles(List<Article> articles) {
        List<DatabaseArticle> databaseArticles = new ArrayList<>();
        for (Article article : articles) {
            String articleSource = article.getSource() != null ? article.getSource().getName() : "Unknown";
            if (article.getUrl() != null) {
                databaseArticles.add(new DatabaseArticle(article.getUrl(), articleSource,
                        article.getAuthor(), article.getTitle(), article.getDescription(), article.getUrlToImage(), article.getPublishedAt()));
            }
        }
        return databaseArticles;
    }
}
