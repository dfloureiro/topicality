package com.dfl.topicality.settings;

import android.content.Context;
import android.content.SharedPreferences;

import dfl.com.newsapikotin.enums.Country;
import dfl.com.newsapikotin.enums.Language;

/**
 * Created by loureiro on 18-02-2018.
 */

public class UserSettingsPersistence {

    private static final String PREFS_KEY = "com.dfl.topicality";
    private static final String FIRST_BOOT_KEY = "FIRST_BOOT_KEY";
    private static final String COUNTRY_CODE_KEY = "COUNTRY_CODE_KEY";
    private static final String LANGUAGE_CODE_KEY = "LANGUAGE_CODE_KEY";

    private final SharedPreferences sharedPreferences;
    private final LocalRepository localRepository;

    public UserSettingsPersistence(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        localRepository = new LocalRepository(context, this);
    }

    public void setFirstBoot() {
        sharedPreferences.edit().putBoolean(FIRST_BOOT_KEY, false).apply();
    }

    public boolean isFirstBoot() {
        return sharedPreferences.getBoolean(FIRST_BOOT_KEY, true);
    }

    public void setCountry(String country) {
        sharedPreferences.edit().putString(COUNTRY_CODE_KEY, country).apply();
    }

    public Country getCountry() {
        return Country.valueOf(sharedPreferences.getString(COUNTRY_CODE_KEY, localRepository.getCountry()));
    }

    public void setLanguage(String language) {
        sharedPreferences.edit().putString(LANGUAGE_CODE_KEY, language).apply();
    }

    public Language getLanguage() {
        return Language.valueOf(sharedPreferences.getString(COUNTRY_CODE_KEY, localRepository.getLanguage()));
    }
}
