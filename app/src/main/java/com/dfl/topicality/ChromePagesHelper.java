package com.dfl.topicality;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

/**
 * Created by loureiro on 02-02-2018.
 */

public class ChromePagesHelper {

    public static void openChromePageHelper(Context context, @NonNull String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

}
