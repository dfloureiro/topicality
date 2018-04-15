package com.dfl.topicality.injection.components;

import com.dfl.topicality.injection.modules.SavedArticlesModule;
import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.saved.SavedArticlesFragment;

import dagger.Component;

@PerFragment
@Component(modules = SavedArticlesModule.class, dependencies = TopicalityComponent.class)
public interface SavedArticlesComponent {

    void inject(SavedArticlesFragment savedArticlesFragment);
}
