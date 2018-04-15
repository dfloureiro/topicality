package com.dfl.topicality.injection.components;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.injection.modules.MainActivityModule;
import com.dfl.topicality.injection.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(modules = MainActivityModule.class, dependencies = TopicalityComponent.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}
