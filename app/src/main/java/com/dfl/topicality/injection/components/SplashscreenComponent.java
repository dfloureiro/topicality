package com.dfl.topicality.injection.components;

import com.dfl.topicality.injection.modules.SplashScreenModule;
import com.dfl.topicality.injection.scopes.PerActivity;
import com.dfl.topicality.splashscreen.SplashScreenActivity;

import dagger.Component;

@PerActivity
@Component(modules = SplashScreenModule.class, dependencies = TopicalityComponent.class)
public interface SplashscreenComponent {

    void inject(SplashScreenActivity splashScreenActivity);
}
