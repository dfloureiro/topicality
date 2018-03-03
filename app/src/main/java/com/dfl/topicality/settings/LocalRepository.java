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

    LocalRepository(Context context) {
        locale = context.getResources().getConfiguration().locale.getCountry();
        language = context.getResources().getConfiguration().locale.getLanguage();
        countryCodes = context.getResources().getStringArray(R.array.country_codes);
        languageCodes = context.getResources().getStringArray(R.array.language_codes);
    }


    String getCountry() {
        for (String countryCode : Arrays.asList(countryCodes)) {
            if (countryCode.equalsIgnoreCase(locale)) {
                return countryCode.toUpperCase();
            }
        }
        return DEFAULT_COUNTRY;
    }

    String getLanguage() {
        for (String languageCode : Arrays.asList(languageCodes)) {
            if (languageCode.equalsIgnoreCase(language)) {
                return languageCode.toUpperCase();
            }
        }
        return DEFAULT_LANGUAGE;
    }
}
