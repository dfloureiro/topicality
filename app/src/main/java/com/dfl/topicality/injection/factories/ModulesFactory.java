package com.dfl.topicality.injection.factories;

import android.content.Context;
import android.os.Bundle;

import com.dfl.topicality.MainActivity;
import com.dfl.topicality.injection.modules.ArticleCardsEverythingModule;
import com.dfl.topicality.injection.modules.ArticleCardsTopModule;
import com.dfl.topicality.injection.modules.CompositeDisposableModule;
import com.dfl.topicality.injection.modules.ContextModule;
import com.dfl.topicality.injection.modules.FragmentContextModule;
import com.dfl.topicality.injection.modules.ImageLoaderModule;
import com.dfl.topicality.injection.modules.MainActivityModule;
import com.dfl.topicality.injection.modules.NewsModule;
import com.dfl.topicality.injection.modules.SavedArticlesModule;
import com.dfl.topicality.injection.modules.SplashScreenModule;
import com.dfl.topicality.injection.modules.TopicalityModule;
import com.dfl.topicality.news.NewsFragment;
import com.dfl.topicality.news.article.everything.ArticleCardsEverythingFragment;
import com.dfl.topicality.news.article.top.ArticleCardsTopFragment;
import com.dfl.topicality.saved.SavedArticlesFragment;
import com.dfl.topicality.splashscreen.SplashScreenActivity;

public class ModulesFactory {

    public ArticleCardsEverythingModule getArticleCardsEverythingModule(ArticleCardsEverythingFragment articleCardsEverythingFragment, Bundle bundle) {
        return new ArticleCardsEverythingModule(articleCardsEverythingFragment, bundle);
    }

    public ArticleCardsTopModule getArticleCardsTopModule(ArticleCardsTopFragment articleCardsTopFragment, Bundle bundle) {
        return new ArticleCardsTopModule(articleCardsTopFragment, bundle);
    }

    public ContextModule getContextModule(Context context) {
        return new ContextModule(context);
    }

    public FragmentContextModule getFragmentContextModule(Context context) {
        return new FragmentContextModule(context);
    }

    public ImageLoaderModule getImageLoaderModule() {
        return new ImageLoaderModule();
    }

    public MainActivityModule getMainActivityModule(MainActivity mainActivity) {
        return new MainActivityModule(mainActivity);
    }

    public NewsModule getNewsModule(NewsFragment newsFragment) {
        return new NewsModule(newsFragment);
    }

    public SavedArticlesModule getSavedArticlesModule(SavedArticlesFragment savedArticlesFragment) {
        return new SavedArticlesModule(savedArticlesFragment);
    }

    public SplashScreenModule getSplashScreenModule(SplashScreenActivity splashScreenActivity) {
        return new SplashScreenModule(splashScreenActivity);
    }

    public TopicalityModule getTopicalityModule() {
        return new TopicalityModule();
    }

    public CompositeDisposableModule getCompositeDisposableModule() {
        return new CompositeDisposableModule();
    }
}
