package com.dfl.topicality.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by loureiro on 17-02-2018.
 */

public class DatabaseInteractor {

    private AppDatabase appDatabase;

    public DatabaseInteractor(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "topicality_saved_articles_database").build();
    }

    public Completable insertAll(DatabaseArticle... databaseArticles) {
        return Completable.fromAction(() ->
                appDatabase.databaseArticleDao().insertAll(databaseArticles));
    }

    public Flowable<List<DatabaseArticle>> getAll() {
        return appDatabase.databaseArticleDao().getAll();
    }

    public Completable deleteWhereUrl(String url) {
        return Completable.fromAction(() -> appDatabase.databaseArticleDao().deleteWhereUrl(url));
    }
}
