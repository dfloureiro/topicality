package com.dfl.topicality.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.dfl.topicality.R;

/**
 * Created by loureiro on 03-03-2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesMode(UserSettingsPersistence.PREFS_MODE);
        getPreferenceManager().setSharedPreferencesName(UserSettingsPersistence.PREFS_KEY);
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
