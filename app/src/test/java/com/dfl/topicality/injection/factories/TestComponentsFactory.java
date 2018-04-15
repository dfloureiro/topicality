package com.dfl.topicality.injection.factories;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.injection.components.ArticleCardsEverythingComponent;
import com.dfl.topicality.injection.components.ArticleCardsTopComponent;
import com.dfl.topicality.injection.components.MainActivityComponent;
import com.dfl.topicality.injection.components.NewsComponent;
import com.dfl.topicality.injection.components.SavedArticlesComponent;
import com.dfl.topicality.injection.components.SplashscreenComponent;
import com.dfl.topicality.news.NewsFragment;
import com.dfl.topicality.news.article.everything.ArticleCardsEverythingFragment;
import com.dfl.topicality.news.article.top.ArticleCardsTopFragment;
import com.dfl.topicality.saved.SavedArticlesFragment;
import com.dfl.topicality.splashscreen.SplashScreenActivity;

import static org.mockito.Mockito.mock;

public class TestComponentsFactory extends ComponentsFactory {

    private final ArticleCardsEverythingComponent articleCardsEverythingComponent;
    private final ArticleCardsTopComponent articleCardsTopComponent;
    private final MainActivityComponent mainActivityComponent;
    private final NewsComponent newsComponent;
    private final SavedArticlesComponent savedArticlesComponent;
    private final SplashscreenComponent splashscreenComponent;

    public TestComponentsFactory(TopicalityApplication topicalityApplication, ModulesFactory modulesFactory) {
        super(topicalityApplication, modulesFactory);
        articleCardsEverythingComponent = mock(ArticleCardsEverythingComponent.class);
        articleCardsTopComponent = mock(ArticleCardsTopComponent.class);
        mainActivityComponent = mock(MainActivityComponent.class);
        newsComponent = mock(NewsComponent.class);
        savedArticlesComponent = mock(SavedArticlesComponent.class);
        splashscreenComponent = mock(SplashscreenComponent.class);
    }

    @Override
    public ArticleCardsEverythingComponent getArticleCardsEverythingComponent(ArticleCardsEverythingFragment articleCardsEverythingFragment) {
        return articleCardsEverythingComponent;
    }

    @Override
    public ArticleCardsTopComponent getArticleCardsTopComponent(ArticleCardsTopFragment articleCardsTopFragment) {
        return articleCardsTopComponent;
    }

    @Override
    public MainActivityComponent getMainActivityComponent(MainActivity mainActivity) {
        return mainActivityComponent;
    }

    @Override
    public NewsComponent getNewsComponent(NewsFragment newsFragment) {
        return newsComponent;
    }

    @Override
    public SavedArticlesComponent getSavedArticlesComponent(SavedArticlesFragment savedArticlesFragment) {
        return savedArticlesComponent;
    }

    @Override
    public SplashscreenComponent getSplashscreenComponent(SplashScreenActivity splashScreenActivity) {
        return splashscreenComponent;
    }
}
