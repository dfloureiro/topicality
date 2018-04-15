package com.dfl.topicality.news.article.everything;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.news.article.ArticleCardsFragment;

import java.util.Objects;

import dfl.com.newsapikotin.enums.Language;

public class ArticleCardsEverythingFragment extends ArticleCardsFragment {

    public final static String LANGUAGE_KEY = "LANGUAGE_KEY";

    public static ArticleCardsEverythingFragment newInstance(Language language) {
        Bundle bundle = new Bundle();
        bundle.putString(LANGUAGE_KEY, language.name());
        ArticleCardsEverythingFragment articleCardsEverythingFragment = new ArticleCardsEverythingFragment();
        articleCardsEverythingFragment.setArguments(bundle);
        return articleCardsEverythingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopicalityApplication.get(Objects.requireNonNull(getActivity()))
                .getComponentsFactory()
                .getArticleCardsEverythingComponent(this)
                .inject(this);
    }
}
