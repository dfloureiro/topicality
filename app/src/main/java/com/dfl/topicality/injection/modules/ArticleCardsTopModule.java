package com.dfl.topicality.injection.modules;

import android.os.Bundle;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.news.article.ArticleCardsAdapter;
import com.dfl.topicality.news.article.ArticleCardsFragment;
import com.dfl.topicality.news.article.ArticleCardsPresenterInterface;
import com.dfl.topicality.news.article.top.ArticleCardsTopFragment;
import com.dfl.topicality.news.article.top.ArticleCardsTopPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import dfl.com.newsapikotin.NewsApi;
import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;
import io.reactivex.disposables.CompositeDisposable;

@Module(includes = {ImageLoaderModule.class, CompositeDisposableModule.class})
public class ArticleCardsTopModule {

    private final ArticleCardsTopFragment articleCardsTopFragment;
    private Category category;
    private Country country;
    private final String q;

    public ArticleCardsTopModule(ArticleCardsTopFragment articleCardsTopFragment, Bundle arguments) {
        this.articleCardsTopFragment = articleCardsTopFragment;
        if (arguments.containsKey(ArticleCardsTopFragment.CATEGORY_KEY)) {
            category = Category.valueOf(arguments.getString(ArticleCardsTopFragment.CATEGORY_KEY, null));
        }
        if (arguments.containsKey(ArticleCardsTopFragment.COUNTRY_KEY)) {
            country = Country.valueOf(arguments.getString(ArticleCardsTopFragment.COUNTRY_KEY, null));
        }
        q = arguments.getString(ArticleCardsFragment.Q_KEY, null);
    }

    @PerFragment
    @Provides
    public ArticleCardsPresenterInterface articleCardsPresenterInterface(NewsApi newsApi, DatabaseInteractor databaseInteractor, CompositeDisposable compositeDisposable) {
        return new ArticleCardsTopPresenter(articleCardsTopFragment, newsApi, databaseInteractor, category, country, q, 1, compositeDisposable);
    }

    @PerFragment
    @Provides
    public ArticleCardsAdapter articleCardsAdapter(ImageLoader imageLoader, ArticleCardsPresenterInterface articleCardsPresenterInterface) {
        return new ArticleCardsAdapter(new ArrayList<>(0), imageLoader, articleCardsPresenterInterface);
    }
}
