package com.dfl.topicality.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.dfl.topicality.R;

/**
 * Created by loureiro on 03-03-2018.
 */

public class SettingsFragment extends PreferenceFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(UserSettingsPersistence.PREFS_MODE);
        getPreferenceManager().setSharedPreferencesName(UserSettingsPersistence.PREFS_KEY);
        addPreferencesFromResource(R.xml.preferences);
    }
}
