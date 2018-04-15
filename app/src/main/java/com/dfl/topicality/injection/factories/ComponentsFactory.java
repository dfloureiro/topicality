package com.dfl.topicality.injection.factories;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.injection.components.ArticleCardsEverythingComponent;
import com.dfl.topicality.injection.components.ArticleCardsTopComponent;
import com.dfl.topicality.injection.components.DaggerArticleCardsEverythingComponent;
import com.dfl.topicality.injection.components.DaggerArticleCardsTopComponent;
import com.dfl.topicality.injection.components.DaggerMainActivityComponent;
import com.dfl.topicality.injection.components.DaggerNewsComponent;
import com.dfl.topicality.injection.components.DaggerSavedArticlesComponent;
import com.dfl.topicality.injection.components.DaggerSplashscreenComponent;
import com.dfl.topicality.injection.components.DaggerTopicalityComponent;
import com.dfl.topicality.injection.components.MainActivityComponent;
import com.dfl.topicality.injection.components.NewsComponent;
import com.dfl.topicality.injection.components.SavedArticlesComponent;
import com.dfl.topicality.injection.components.SplashscreenComponent;
import com.dfl.topicality.injection.components.TopicalityComponent;
import com.dfl.topicality.news.NewsFragment;
import com.dfl.topicality.news.article.everything.ArticleCardsEverythingFragment;
import com.dfl.topicality.news.article.top.ArticleCardsTopFragment;
import com.dfl.topicality.saved.SavedArticlesFragment;
import com.dfl.topicality.splashscreen.SplashScreenActivity;

public class ComponentsFactory {

    private TopicalityComponent topicalityComponent;
    private ModulesFactory modulesFactory;

    public ComponentsFactory(TopicalityApplication topicalityApplication, ModulesFactory modulesFactory) {
        topicalityComponent = DaggerTopicalityComponent.builder()
                .contextModule(modulesFactory.getContextModule(topicalityApplication))
                .topicalityModule(modulesFactory.getTopicalityModule())
                .build();
        this.modulesFactory = modulesFactory;
    }

    public ArticleCardsEverythingComponent getArticleCardsEverythingComponent(ArticleCardsEverythingFragment articleCardsEverythingFragment) {
        return DaggerArticleCardsEverythingComponent
                .builder()
                .compositeDisposableModule(modulesFactory.getCompositeDisposableModule())
                .articleCardsEverythingModule(modulesFactory.getArticleCardsEverythingModule(articleCardsEverythingFragment, articleCardsEverythingFragment.getArguments()))
                .fragmentContextModule(modulesFactory.getFragmentContextModule(articleCardsEverythingFragment.getContext()))
                .imageLoaderModule(modulesFactory.getImageLoaderModule())
                .topicalityComponent(topicalityComponent)
                .build();
    }

    public ArticleCardsTopComponent getArticleCardsTopComponent(ArticleCardsTopFragment articleCardsTopFragment) {
        return DaggerArticleCardsTopComponent
                .builder()
                .compositeDisposableModule(modulesFactory.getCompositeDisposableModule())
                .articleCardsTopModule(modulesFactory.getArticleCardsTopModule(articleCardsTopFragment, articleCardsTopFragment.getArguments()))
                .fragmentContextModule(modulesFactory.getFragmentContextModule(articleCardsTopFragment.getContext()))
                .imageLoaderModule(modulesFactory.getImageLoaderModule())
                .topicalityComponent(topicalityComponent)
                .build();
    }

    public MainActivityComponent getMainActivityComponent(MainActivity mainActivity) {
        return DaggerMainActivityComponent
                .builder()
                .mainActivityModule(modulesFactory.getMainActivityModule(mainActivity))
                .topicalityComponent(topicalityComponent)
                .build();
    }

    public NewsComponent getNewsComponent(NewsFragment newsFragment) {
        return DaggerNewsComponent
                .builder()
                .newsModule(modulesFactory.getNewsModule(newsFragment))
                .topicalityComponent(topicalityComponent)
                .build();
    }

    public SavedArticlesComponent getSavedArticlesComponent(SavedArticlesFragment savedArticlesFragment) {
        return DaggerSavedArticlesComponent
                .builder()
                .savedArticlesModule(modulesFactory.getSavedArticlesModule(savedArticlesFragment))
                .fragmentContextModule(modulesFactory.getFragmentContextModule(savedArticlesFragment.getContext()))
                .imageLoaderModule(modulesFactory.getImageLoaderModule())
                .topicalityComponent(topicalityComponent)
                .build();
    }

    public SplashscreenComponent getSplashscreenComponent(SplashScreenActivity splashScreenActivity) {
        return DaggerSplashscreenComponent
                .builder()
                .splashScreenModule(modulesFactory.getSplashScreenModule(splashScreenActivity))
                .topicalityComponent(topicalityComponent)
                .build();
    }
}
