package com.dfl.topicality.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by loureiro on 31-01-2018.
 */

@Database(entities = {DatabaseArticle.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    abstract DatabaseArticleDao databaseArticleDao();
}
