package com.dfl.topicality.saved;

import com.dfl.topicality.base.BasePresenter;
import com.dfl.topicality.base.BaseState;
import com.dfl.topicality.base.BaseView;
import com.dfl.topicality.database.DatabaseArticle;

/**
 * Created by loureiro on 31-01-2018.
 */

public interface SavedArticlesContract {

    interface View extends BaseView {

        void addArticle(DatabaseArticle databaseArticle);

        void removeArticle(int viewHolderPosition);

        void showSnackBar(String message);

        void hideNoBookmarksLayout();

        void showNoBookmarksLayout();
    }

    interface Presenter extends BasePresenter<SavedArticlesContract.State> {

        /**
         * instantiates and returns a state with variables to save
         *
         * @return state to save
         */
        SavedArticlesContract.State getState();

        void getAllArticles();

        void removeFromSaved(String url, int viewHolderPosition);

        void upsertFavoriteSourceClicks(String sourceDomain);
    }

    interface State extends BaseState {


    }
}
