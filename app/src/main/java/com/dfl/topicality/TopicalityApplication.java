package com.dfl.topicality;

import android.app.Application;

import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.settings.UserSettingsPersistence;
import com.squareup.leakcanary.LeakCanary;

import dfl.com.newsapikotin.NewsApi;

/**
 * Created by loureiro on 31-01-2018.
 */

public class TopicalityApplication extends Application {

    private UserSettingsPersistence userSettingsPersistence;
    private DatabaseInteractor databaseInteractor;
    private NewsApi requestFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public UserSettingsPersistence getUserSettingsPersistence() {
        if (userSettingsPersistence == null) {
            userSettingsPersistence = new UserSettingsPersistence(getApplicationContext());
        }
        return userSettingsPersistence;
    }

    public DatabaseInteractor getDatabase() {
        if (databaseInteractor == null) {
            databaseInteractor = new DatabaseInteractor(getApplicationContext());
        }
        return databaseInteractor;
    }

    public NewsApi getRequestFactory() {
        if (requestFactory == null) {
            requestFactory = new NewsApi(BuildConfig.NEWS_API_KEY, getCacheDir(), 60, 10 * 1024 * 1024L, 45L, 45L);
        }
        return requestFactory;
    }
}
