package com.dfl.topicality.injection.modules;

import android.content.Context;

import com.dfl.topicality.BuildConfig;
import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.injection.scopes.TopicalityApplicationScope;
import com.dfl.topicality.settings.UserSettingsPersistence;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import dfl.com.newsapikotin.NewsApi;

@Module(includes = ContextModule.class)
public class TopicalityModule {

    @TopicalityApplicationScope
    @Provides
    public UserSettingsPersistence userSettingsPersistence(Context context) {
        return new UserSettingsPersistence(context);
    }

    @TopicalityApplicationScope
    @Provides
    public DatabaseInteractor databaseInteractor(Context context) {
        return new DatabaseInteractor(context);
    }

    @TopicalityApplicationScope
    @Provides
    public NewsApi newsApi(File cacheDir) {
        return new NewsApi(BuildConfig.NEWS_API_KEY, cacheDir, 60, 10 * 1024 * 1024L, 45L, 45L);
    }
}
