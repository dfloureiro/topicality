package com.dfl.topicality;

import android.app.Activity;
import android.app.Application;

import com.dfl.topicality.injection.factories.ComponentsFactory;
import com.dfl.topicality.injection.factories.ModulesFactory;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by loureiro on 31-01-2018.
 */

public class TopicalityApplication extends Application {

    private ComponentsFactory componentsFactory;

    public static TopicalityApplication get(Activity activity) {
        return (TopicalityApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        componentsFactory = new ComponentsFactory(this, new ModulesFactory());
    }

    public ComponentsFactory getComponentsFactory() {
        return componentsFactory;
    }
}