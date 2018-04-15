package com.dfl.topicality.injection.modules;

import android.content.Context;

import com.dfl.topicality.injection.scopes.PerFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentContextModule {

    Context context;

    public FragmentContextModule(Context context) {
        this.context = context;
    }

    @PerFragment
    @Provides
    public Context context() {
        return context;
    }
}
