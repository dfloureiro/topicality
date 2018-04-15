package com.dfl.topicality.injection.components;

import com.dfl.topicality.injection.modules.ArticleCardsEverythingModule;
import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.news.article.everything.ArticleCardsEverythingFragment;

import dagger.Component;

@PerFragment
@Component(modules = {ArticleCardsEverythingModule.class}, dependencies = TopicalityComponent.class)
public interface ArticleCardsEverythingComponent {

    void inject(ArticleCardsEverythingFragment articleCardsEverythingFragment);
}
