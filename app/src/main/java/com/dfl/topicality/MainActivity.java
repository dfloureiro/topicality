package com.dfl.topicality;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.dfl.topicality.news.NewsFragment;
import com.dfl.topicality.news.article.ArticleCardsFragment;
import com.dfl.topicality.saved.SavedArticlesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import dfl.com.newsapikotin.enums.Language;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.activity_main_container)
    UnscrollableViewPager viewPager;

    private Language language;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        language = ((TopicalityApplication) getApplication()).getUserSettingsPersistence().getLanguage();

        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        viewPager.setOffscreenPageLimit(3);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_top:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.action_news:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.action_saved:
                            viewPager.setCurrentItem(2);
                            break;
                    }
                    return true;
                });
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsFragment.newInstance();
                case 1:
                    return ArticleCardsFragment.newInstance(language);
                case 2:
                    return SavedArticlesFragment.newInstance();
                default:
                    return NewsFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
