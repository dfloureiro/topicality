package com.dfl.topicality.news.article.top;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.news.article.ArticleCardsFragment;

import java.util.Objects;

import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;

public class ArticleCardsTopFragment extends ArticleCardsFragment {

    public final static String CATEGORY_KEY = "CATEGORY_KEY";
    public final static String COUNTRY_KEY = "COUNTRY_KEY";

    public static ArticleCardsTopFragment newInstance(Category category, Country country) {
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_KEY, category.name());
        bundle.putString(COUNTRY_KEY, country.name());
        ArticleCardsTopFragment articleCardsFragment = new ArticleCardsTopFragment();
        articleCardsFragment.setArguments(bundle);
        return articleCardsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopicalityApplication.get(Objects.requireNonNull(getActivity()))
                .getComponentsFactory()
                .getArticleCardsTopComponent(this)
                .inject(this);
    }
}
