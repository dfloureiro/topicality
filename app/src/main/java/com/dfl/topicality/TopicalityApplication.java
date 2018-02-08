package com.dfl.topicality;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dfl.topicality.database.AppDatabase;

/**
 * Created by loureiro on 31-01-2018.
 */

public class TopicalityApplication extends Application {


    private AppDatabase database;

    public AppDatabase getDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "topicality_saved_articles_database").build();
        }
        return database;
    }

}
