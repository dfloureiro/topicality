package com.dfl.topicality.injection.components;

import com.dfl.topicality.injection.modules.ArticleCardsTopModule;
import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.news.article.top.ArticleCardsTopFragment;

import dagger.Component;

@PerFragment
@Component(modules = {ArticleCardsTopModule.class}, dependencies = TopicalityComponent.class)
public interface ArticleCardsTopComponent {

    void inject(ArticleCardsTopFragment articleCardsTopFragment);
}
