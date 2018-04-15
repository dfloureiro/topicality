package com.dfl.topicality.news;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dfl.topicality.R;
import com.dfl.topicality.news.article.top.ArticleCardsTopFragment;

import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;

public class NewsSectionPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;
    private final Country country;

    public NewsSectionPagerAdapter(FragmentManager fragmentManager, Resources resources, Country country) {
        super(fragmentManager);
        this.resources = resources;
        this.country = country;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ArticleCardsTopFragment.newInstance(Category.GENERAL, country);
            case 1:
                return ArticleCardsTopFragment.newInstance(Category.SPORTS, country);
            case 2:
                return ArticleCardsTopFragment.newInstance(Category.TECHNOLOGY, country);
            case 3:
                return ArticleCardsTopFragment.newInstance(Category.BUSINESS, country);
            case 4:
                return ArticleCardsTopFragment.newInstance(Category.ENTERTAINMENT, country);
            case 5:
                return ArticleCardsTopFragment.newInstance(Category.SCIENCE, country);
            case 6:
                return ArticleCardsTopFragment.newInstance(Category.HEALTH, country);
            default:
                return ArticleCardsTopFragment.newInstance(Category.GENERAL, country);
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.general_category);
            case 1:
                return resources.getString(R.string.sports_category);
            case 2:
                return resources.getString(R.string.technology_category);
            case 3:
                return resources.getString(R.string.business_category);
            case 4:
                return resources.getString(R.string.entertainment_category);
            case 5:
                return resources.getString(R.string.science_category);
            case 6:
                return resources.getString(R.string.health_category);
            default:
                return resources.getString(R.string.general_category);
        }
    }


}
