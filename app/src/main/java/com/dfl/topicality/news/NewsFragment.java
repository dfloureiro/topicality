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
import com.dfl.topicality.UnscrollableViewPager;
import com.dfl.topicality.news.article.ArticleCardsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;

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

        country = Country.PT;
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
                    return ArticleCardsFragment.newInstance(Category.GENERAL, country, null, null);
                case 1:
                    return ArticleCardsFragment.newInstance(Category.SPORTS, country, null, null);
                case 2:
                    return ArticleCardsFragment.newInstance(Category.TECHNOLOGY, country, null, null);
                case 3:
                    return ArticleCardsFragment.newInstance(Category.BUSINESS, country, null, null);
                case 4:
                    return ArticleCardsFragment.newInstance(Category.ENTERTAINMENT, country, null, null);
                case 5:
                    return ArticleCardsFragment.newInstance(Category.SCIENCE, country, null, null);
                case 6:
                    return ArticleCardsFragment.newInstance(Category.HEALTH, country, null, null);
                default:
                    return ArticleCardsFragment.newInstance(Category.GENERAL, country, null, null);
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
                    return "General";
                case 1:
                    return "Sports";
                case 2:
                    return "Technology";
                case 3:
                    return "Business";
                case 4:
                    return "Entertainment";
                case 5:
                    return "Science";
                case 6:
                    return "Health";
                default:
                    return "General";
            }
        }


    }
}
