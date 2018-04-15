package com.dfl.topicality.injection.modules;

import android.content.Context;

import com.dfl.topicality.injection.scopes.TopicalityApplicationScope;

import java.io.File;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @TopicalityApplicationScope
    @Provides
    public Context context() {
        return context.getApplicationContext();
    }

    @TopicalityApplicationScope
    @Provides
    File cacheDir() {
        return context.getCacheDir();
    }

}
