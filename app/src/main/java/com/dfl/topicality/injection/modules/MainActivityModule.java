package com.dfl.topicality.injection.modules;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.MainViewPagerAdapter;
import com.dfl.topicality.injection.scopes.PerActivity;
import com.dfl.topicality.settings.UserSettingsPersistence;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @PerActivity
    @Provides
    public MainViewPagerAdapter mainViewPagerAdapter(UserSettingsPersistence userSettingsPersistence) {
        return new MainViewPagerAdapter(mainActivity.getSupportFragmentManager(), userSettingsPersistence.getLanguage());
    }
}
