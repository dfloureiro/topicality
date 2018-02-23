package com.dfl.topicality.news;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfl.topicality.R;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.UnscrollableViewPager;
import com.dfl.topicality.news.article.ArticleCardsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;
import dfl.com.newsapikotin.enums.Language;

/**
 * Created by loureiro on 30-01-2018.
 */

public class NewsFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    UnscrollableViewPager viewPager;

    private Unbinder unbinder;
    private Country country;
    private Language language;

    public NewsFragment() {

    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        country = ((TopicalityApplication) getActivity().getApplication()).getUserSettingsPersistence().getCountry();
        language = ((TopicalityApplication) getActivity().getApplication()).getUserSettingsPersistence().getLanguage();
        viewPager.setAdapter(new SectionPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {

        SectionPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ArticleCardsFragment.newInstance(language, country);
                case 1:
                    return ArticleCardsFragment.newInstance(Category.GENERAL, country);
                case 2:
                    return ArticleCardsFragment.newInstance(Category.SPORTS, country);
                case 3:
                    return ArticleCardsFragment.newInstance(Category.TECHNOLOGY, country);
                case 4:
                    return ArticleCardsFragment.newInstance(Category.BUSINESS, country);
                case 5:
                    return ArticleCardsFragment.newInstance(Category.ENTERTAINMENT, country);
                case 6:
                    return ArticleCardsFragment.newInstance(Category.SCIENCE, country);
                case 7:
                    return ArticleCardsFragment.newInstance(Category.HEALTH, country);
                default:
                    return ArticleCardsFragment.newInstance(Category.GENERAL, country);
            }
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.for_you_category);
                case 1:
                    return getString(R.string.general_category);
                case 2:
                    return getString(R.string.sports_category);
                case 3:
                    return getString(R.string.technology_category);
                case 4:
                    return getString(R.string.business_category);
                case 5:
                    return getString(R.string.entertainment_category);
                case 6:
                    return getString(R.string.science_category);
                case 7:
                    return getString(R.string.health_category);
                default:
                    return getString(R.string.for_you_category);
            }
        }


    }
}
