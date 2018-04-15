package com.dfl.topicality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.activity_main_container)
    UnscrollableViewPager viewPager;

    @Inject
    protected MainViewPagerAdapter mainViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TopicalityApplication.get(this).getComponentsFactory().getMainActivityComponent(this).inject(this);

        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mainViewPagerAdapter.getCount());

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
                        case R.id.action_settings:
                            viewPager.setCurrentItem(3);
                    }
                    return true;
                });
    }
}
