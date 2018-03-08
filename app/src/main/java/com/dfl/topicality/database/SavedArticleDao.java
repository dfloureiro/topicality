package com.dfl.topicality.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by loureiro on 31-01-2018.
 */

@Dao
interface SavedArticleDao {

    @Query("SELECT * FROM databasearticle WHERE is_favourite = 1")
    Flowable<List<DatabaseArticle>> getAll();

    @Query("SELECT * FROM databasearticle WHERE url IN(:urls)")
    Maybe<List<DatabaseArticle>> getWhereUrl(List<String> urls);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DatabaseArticle... databaseArticles);

    @Query("UPDATE databasearticle SET is_favourite = 0 WHERE url = :urlToDeleteFromSaved")
    void deleteFromSavedWhereUrl(String urlToDeleteFromSaved);

    @Query("DELETE FROM databasearticle")
    void deleteAll();
}
