package com.dfl.topicality.settings;

import android.content.Context;

import com.dfl.topicality.R;

import java.util.Arrays;

/**
 * Created by loureiro on 18-02-2018.
 */

class LocalRepository {

    private static final String DEFAULT_COUNTRY = "US";
    private static final String DEFAULT_LANGUAGE = "EN";

    private String locale;
    private String language;
    private String[] countryCodes;
    private String[] languageCodes;
    private UserSettingsPersistence userSettingsPersistence;

    LocalRepository(Context context, UserSettingsPersistence userSettingsPersistence) {
        locale = context.getResources().getConfiguration().locale.getCountry();
        language = context.getResources().getConfiguration().locale.getLanguage();
        countryCodes = context.getResources().getStringArray(R.array.country_codes);
        languageCodes = context.getResources().getStringArray(R.array.language_codes);
        this.userSettingsPersistence = userSettingsPersistence;
    }


    String getCountry() {
        for (String countryCode : Arrays.asList(countryCodes)) {
            if (countryCode.equalsIgnoreCase(locale)) {
                userSettingsPersistence.setCountry(countryCode.toUpperCase());
                return countryCode.toUpperCase();
            }
        }
        userSettingsPersistence.setCountry(DEFAULT_COUNTRY);
        return DEFAULT_COUNTRY;
    }

    String getLanguage() {
        for (String languageCode : Arrays.asList(languageCodes)) {
            if (languageCode.equalsIgnoreCase(language)) {
                userSettingsPersistence.setLanguage(languageCode.toUpperCase());
                return languageCode.toUpperCase();
            }
        }
        userSettingsPersistence.setLanguage(DEFAULT_LANGUAGE);
        return DEFAULT_LANGUAGE;
    }
}
