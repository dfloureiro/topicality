package com.dfl.topicality.injection.modules;

import android.os.Bundle;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.injection.scopes.PerFragment;
import com.dfl.topicality.news.article.ArticleCardsAdapter;
import com.dfl.topicality.news.article.ArticleCardsFragment;
import com.dfl.topicality.news.article.ArticleCardsPresenterInterface;
import com.dfl.topicality.news.article.everything.ArticleCardsEverythingFragment;
import com.dfl.topicality.news.article.everything.ArticleCardsEverythingPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import dfl.com.newsapikotin.NewsApi;
import dfl.com.newsapikotin.enums.Language;
import io.reactivex.disposables.CompositeDisposable;

@Module(includes = {ImageLoaderModule.class, CompositeDisposableModule.class})
public class ArticleCardsEverythingModule {

    private final ArticleCardsEverythingFragment articleCardsEverythingFragment;
    private Language language;
    private String q;

    public ArticleCardsEverythingModule(ArticleCardsEverythingFragment articleCardsEverythingFragment, Bundle arguments) {
        this.articleCardsEverythingFragment = articleCardsEverythingFragment;
        if (arguments.containsKey(ArticleCardsEverythingFragment.LANGUAGE_KEY)) {
            language = Language.valueOf(arguments.getString(ArticleCardsEverythingFragment.LANGUAGE_KEY, null));
        }
        q = arguments.getString(ArticleCardsFragment.Q_KEY, null);
    }

    @PerFragment
    @Provides
    public ArticleCardsPresenterInterface articleCardsPresenterInterface(NewsApi newsApi, DatabaseInteractor databaseInteractor, CompositeDisposable compositeDisposable) {
        return new ArticleCardsEverythingPresenter(articleCardsEverythingFragment, newsApi, databaseInteractor, language, q, 1, compositeDisposable);
    }

    // TODO: 10-04-2018 can this be in a different module?
    @PerFragment
    @Provides
    public ArticleCardsAdapter articleCardsAdapter(ImageLoader imageLoader, ArticleCardsPresenterInterface articleCardsPresenterInterface) {
        return new ArticleCardsAdapter(new ArrayList<>(0), imageLoader, articleCardsPresenterInterface);
    }
}
