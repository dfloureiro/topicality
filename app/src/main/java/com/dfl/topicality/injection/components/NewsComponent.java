package com.dfl.topicality.injection.components;

import com.dfl.topicality.injection.modules.NewsModule;
import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.news.NewsFragment;

import dagger.Component;

@PerFragment
@Component(modules = NewsModule.class, dependencies = TopicalityComponent.class)
public interface NewsComponent {

    void inject(NewsFragment newsFragment);
}
