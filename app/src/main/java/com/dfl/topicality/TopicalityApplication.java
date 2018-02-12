package com.dfl.topicality;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dfl.topicality.database.AppDatabase;
import com.dfl.topicality.network.RequestFactory;

/**
 * Created by loureiro on 31-01-2018.
 */

public class TopicalityApplication extends Application {

    private AppDatabase database;
    private RequestFactory requestFactory;

    public AppDatabase getDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "topicality_saved_articles_database").build();
        }
        return database;
    }

    public RequestFactory getRequestFactory() {
        if (requestFactory == null) {
            requestFactory = new RequestFactory("a9b9d5c92bc249ac976e796fb79d7a33");
        }
        return requestFactory;
    }
}
