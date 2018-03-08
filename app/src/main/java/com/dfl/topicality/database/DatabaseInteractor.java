package com.dfl.topicality.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by loureiro on 17-02-2018.
 */

public class DatabaseInteractor {

    private AppDatabase appDatabase;

    public DatabaseInteractor(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "topicality_saved_articles_database")
                .addMigrations(MigrationHelper.MIGRATION_1_2, MigrationHelper.MIGRATION_2_3)
                .build();
    }

    public Completable insertAllDatabaseArticles(DatabaseArticle... databaseArticles) {
        return Completable.fromAction(() ->
                appDatabase.getSavedArticleDao().insertAll(databaseArticles));
    }

    public Flowable<List<DatabaseArticle>> getAllDatabaseArticles() {
        return appDatabase.getSavedArticleDao().getAll();
    }

    public Maybe<List<DatabaseArticle>> getDatabaseArticleFromUrl(List<String> urlsList) {
        return appDatabase.getSavedArticleDao().getWhereUrl(urlsList);
    }

    public Completable deleteDatabaseArticleFromSavedWhereUrl(String url) {
        return Completable.fromAction(() -> appDatabase.getSavedArticleDao().deleteFromSavedWhereUrl(url));
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

    public Single<List<FavoriteSource>> getAllFavoriteSourcesOrderByInteractionsDes() {
        return appDatabase.getFavoriteSourceDao().getAllOrderByNumberInteractionsDesc();
    }
}
