package com.dfl.topicality.injection.components;

import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.injection.modules.TopicalityModule;
import com.dfl.topicality.injection.scopes.TopicalityApplicationScope;
import com.dfl.topicality.settings.UserSettingsPersistence;

import dagger.Component;
import dfl.com.newsapikotin.NewsApi;

@TopicalityApplicationScope
@Component(modules = {TopicalityModule.class})
public interface TopicalityComponent {

    UserSettingsPersistence getUserSettingsPersistance();

    DatabaseInteractor getDatabaseInteractor();

    NewsApi getNewsApi();
}
