package com.dfl.topicality.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by loureiro on 18-02-2018.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
@Entity
public class FavoriteSource {

    @NonNull
    @PrimaryKey
    private String sourcedomain;
    @ColumnInfo(name = "number_clicks")
    private int numberClicks;
    @ColumnInfo(name = "number_saves")
    private int numberSaves;

    public FavoriteSource(@NonNull String sourcedomain, int numberClicks, int numberSaves) {
        this.sourcedomain = sourcedomain;
        this.numberClicks = numberClicks;
        this.numberSaves = numberSaves;
    }

    @NonNull
    public String getSourcedomain() {
        return sourcedomain;
    }

    public void setSourcedomain(@NonNull String sourcedomain) {
        this.sourcedomain = sourcedomain;
    }

    public int getNumberClicks() {
        return numberClicks;
    }

    public void setNumberClicks(int numberClicks) {
        this.numberClicks = numberClicks;
    }

    public int getNumberSaves() {
        return numberSaves;
    }

    public void setNumberSaves(int numberSaves) {
        this.numberSaves = numberSaves;
    }
}
