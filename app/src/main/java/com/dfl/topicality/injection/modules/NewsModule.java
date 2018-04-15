package com.dfl.topicality.injection.modules;

import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.news.NewsFragment;
import com.dfl.topicality.news.NewsSectionPagerAdapter;
import com.dfl.topicality.settings.UserSettingsPersistence;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsModule {

    private final NewsFragment newsFragment;

    public NewsModule(NewsFragment newsFragment) {
        this.newsFragment = newsFragment;
    }

    @PerFragment
    @Provides
    public NewsSectionPagerAdapter newsSectionPagerAdapter(UserSettingsPersistence userSettingsPersistence) {
        return new NewsSectionPagerAdapter(newsFragment.getFragmentManager(), newsFragment.getResources(), userSettingsPersistence.getCountry());
    }
}
