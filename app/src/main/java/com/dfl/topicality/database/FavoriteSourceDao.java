package com.dfl.topicality.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by loureiro on 18-02-2018.
 */
@Dao
interface FavoriteSourceDao {

    @Query("SELECT * FROM favoritesource ORDER BY number_clicks + number_saves DESC")
    Single<List<FavoriteSource>> getAllOrderByNumberInteractionsDesc();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(FavoriteSource... favoriteSources);

    @Query("UPDATE favoritesource SET number_clicks = number_clicks + 1 WHERE sourcedomain = :sourceDomain")
    void incrementNumberOfClicksOnFavoriteSource(String sourceDomain);

    @Query("UPDATE favoritesource SET number_saves = number_saves + 1 WHERE sourcedomain = :sourceDomain")
    void incrementNumberOfSavesOnFavoriteSource(String sourceDomain);
}
