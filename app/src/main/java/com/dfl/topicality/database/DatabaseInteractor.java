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

    public Completable insertAllDatabaseArticles(DatabaseArticle... databaseArticles) {
        return Completable.fromAction(() ->
                appDatabase.getSavedArticleDao().insertAll(databaseArticles));
    }

    public Flowable<List<DatabaseArticle>> getAllDatabaseArticles() {
        return appDatabase.getSavedArticleDao().getAll();
    }

    public Completable deleteDatabaseArticleWhereUrl(String url) {
        return Completable.fromAction(() -> appDatabase.getSavedArticleDao().deleteWhereUrl(url));
    }

    public Completable upsertFavoriteSourcesClicks(String sourceDomain) {
        return Completable.fromAction(() ->
                appDatabase.updateFavoriteSourceClicks(sourceDomain));
    }

    public Completable upsertFavoriteSourcesSaved(String sourceDomain) {
        return Completable.fromAction(() ->
                appDatabase.updateFavoriteSourceSaved(sourceDomain));
    }

    public Completable insertAllFavoriteSources(FavoriteSource... favoriteSources) {
        return Completable.fromAction(() -> appDatabase.getFavoriteSourceDao().insertAll(favoriteSources));
    }

    public Flowable<List<FavoriteSource>> getAllFavoriteSourcesOrderByInteractionsDes() {
        return appDatabase.getFavoriteSourceDao().getAllOrderByNumberInteractionsDesc();
    }
}
