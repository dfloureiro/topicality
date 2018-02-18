package com.dfl.topicality.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Transaction;

/**
 * Created by loureiro on 31-01-2018.
 */

@Database(entities = {DatabaseArticle.class, FavoriteSource.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    abstract SavedArticleDao getSavedArticleDao();

    abstract FavoriteSourceDao getFavoriteSourceDao();

    @Transaction
    void updateFavoriteSource(String sourceDomain) {
        getFavoriteSourceDao().insertAll(new FavoriteSource(sourceDomain, 0));
        getFavoriteSourceDao().incrementNumberOfClicksOnFavoriteSource(sourceDomain);
    }
}
