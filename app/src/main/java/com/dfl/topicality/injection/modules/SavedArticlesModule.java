package com.dfl.topicality.injection.modules;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.saved.SavedArticlesAdapter;
import com.dfl.topicality.saved.SavedArticlesFragment;
import com.dfl.topicality.saved.SavedArticlesPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module(includes = ImageLoaderModule.class)
public class SavedArticlesModule {

    private final SavedArticlesFragment savedArticlesFragment;

    public SavedArticlesModule(SavedArticlesFragment savedArticlesFragment) {
        this.savedArticlesFragment = savedArticlesFragment;
    }

    @Provides
    @PerFragment
    public SavedArticlesPresenter savedArticlesPresenter(DatabaseInteractor databaseInteractor) {
        return new SavedArticlesPresenter(savedArticlesFragment, databaseInteractor);
    }

    @Provides
    @PerFragment
    public SavedArticlesAdapter savedArticlesAdapter(ImageLoader imageLoader, SavedArticlesPresenter presenter) {
        return new SavedArticlesAdapter(new ArrayList<>(0), imageLoader, presenter);
    }
}
