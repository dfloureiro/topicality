package com.dfl.topicality.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by loureiro on 31-01-2018.
 */

@Dao
interface SavedArticleDao {

    @Query("SELECT * FROM databasearticle")
    Flowable<List<DatabaseArticle>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DatabaseArticle... databaseArticles);

    @Query("DELETE FROM databasearticle WHERE url = :urlToDelete")
    void deleteWhereUrl(String urlToDelete);

    @Query("DELETE FROM databasearticle")
    void deleteAll();
}
