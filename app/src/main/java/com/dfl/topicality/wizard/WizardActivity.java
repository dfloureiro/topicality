package com.dfl.topicality.wizard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dfl.topicality.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WizardActivity extends AppCompatActivity {

    @BindView(R.id.wizard_pager)
    ViewPager viewPager;
    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        ButterKnife.bind(this);

        viewPager.setAdapter(new ScreenSlidePagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_PAGES = 2;

        ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WizardWelcomeFragment.newInstance();
                case 1:
                    return WizardPreferencesFragment.newInstance();
                default:
                    return WizardWelcomeFragment.newInstance();

            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
