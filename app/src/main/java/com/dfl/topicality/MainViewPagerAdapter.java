package com.dfl.topicality;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dfl.topicality.news.NewsFragment;
import com.dfl.topicality.news.article.everything.ArticleCardsEverythingFragment;
import com.dfl.topicality.saved.SavedArticlesFragment;
import com.dfl.topicality.settings.SettingsFragment;

import dfl.com.newsapikotin.enums.Language;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_TABS = 4;
    private final Language language;

    public MainViewPagerAdapter(FragmentManager fragmentManager, Language language) {
        super(fragmentManager);
        this.language = language;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewsFragment.newInstance();
            case 1:
                return ArticleCardsEverythingFragment.newInstance(language);
            case 2:
                return SavedArticlesFragment.newInstance();
            case 3:
                return SettingsFragment.newInstance();
            default:
                return NewsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }
}
