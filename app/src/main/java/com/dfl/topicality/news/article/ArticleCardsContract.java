package com.dfl.topicality.news.article;

/**
 * Created by loureiro on 29-01-2018.
 */

import com.dfl.topicality.base.BasePresenter;
import com.dfl.topicality.base.BaseState;
import com.dfl.topicality.base.BaseView;
import com.dfl.topicality.database.DatabaseArticle;

import java.util.List;

/**
 * Created by Loureiro on 13/11/2017.
 * <p>
 * Main contract between the view, presenter and state implementations
 */

public interface ArticleCardsContract {

    interface View extends BaseView {

        void addArticles(List<DatabaseArticle> articles);

        List<DatabaseArticle> extractRemainingArticles();

        void showNetworkError();

        void showUnknownError();

        void showSnackBar(String message);

        NewsType getTypeOfNews();
    }

    interface Presenter extends BasePresenter<State> {

        /**
         * instantiates and returns a state with variables to save
         *
         * @return state to save
         */
        State getState();

        void getArticles();

        void setArticleAsClicked(DatabaseArticle databaseArticle);

        void setArticleAsViewed(DatabaseArticle databaseArticle);

        void saveArticle(DatabaseArticle article);

        void upsertFavoriteSourceClicks(String sourceDomain);

        void upsertFavoriteSourceSaved(String sourceDomain);
    }

    interface State extends BaseState {

        int getPage();

        String getDomains();

        List<DatabaseArticle> getRemainingArticles();
    }
}
