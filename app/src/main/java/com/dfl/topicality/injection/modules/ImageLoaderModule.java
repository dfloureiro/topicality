package com.dfl.topicality.injection.modules;

import android.content.Context;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.injection.scopes.PerFragment;

import dagger.Module;
import dagger.Provides;

@Module(includes = FragmentContextModule.class)
public class ImageLoaderModule {

    @PerFragment
    @Provides
    public ImageLoader imageLoader(Context context) {
        return new ImageLoader(context);
    }
}
