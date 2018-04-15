package com.dfl.topicality.injection.modules;

import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.injection.scopes.PerActivity;
import com.dfl.topicality.settings.UserSettingsPersistence;
import com.dfl.topicality.splashscreen.SplashScreenActivity;
import com.dfl.topicality.splashscreen.SplashScreenPresenter;

import dagger.Module;
import dagger.Provides;
import dfl.com.newsapikotin.NewsApi;

@Module
public class SplashScreenModule {

    private final SplashScreenActivity splashScreenActivity;

    public SplashScreenModule(SplashScreenActivity splashScreenActivity) {
        this.splashScreenActivity = splashScreenActivity;
    }

    @PerActivity
    @Provides
    public SplashScreenPresenter splashScreenPresenter(NewsApi newsApi, DatabaseInteractor databaseInteractor, UserSettingsPersistence userSettingsPersistence) {
        return new SplashScreenPresenter(splashScreenActivity, newsApi, databaseInteractor, userSettingsPersistence.getCountry(), userSettingsPersistence.getLanguage());
    }
}
