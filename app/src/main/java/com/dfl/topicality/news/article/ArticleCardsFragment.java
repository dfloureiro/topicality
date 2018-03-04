package com.dfl.topicality.news.article;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dfl.topicality.ChromePagesHelper;
import com.dfl.topicality.DomainUtils;
import com.dfl.topicality.R;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.database.DatabaseArticle;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dfl.com.newsapikotin.enums.Category;
import dfl.com.newsapikotin.enums.Country;
import dfl.com.newsapikotin.enums.Language;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsFragment extends Fragment implements ArticleCardsContract.View {

    private final static String CATEGORY_KEY = "CATEGORY_KEY";
    private final static String LANGUAGE_KEY = "LANGUAGE_KEY";
    private final static String COUNTRY_KEY = "COUNTRY_KEY";
    private final static String Q_KEY = "Q_KEY";
    private final static String NO_MORE_ARTICLES_KEY = "NO_MORE_ARTICLES_KEY";

    @BindView(R.id.main_fragment_card_stack_view)
    CardStackView cardStackView;
    @BindView(R.id.main_fragment_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_news_cards_layout)
    ConstraintLayout container;
    @BindView(R.id.no_more_articles_layout)
    RelativeLayout noMoreArticlesLayout;
    @BindView(R.id.network_error_layout)
    RelativeLayout networkErrorLayout;
    @BindView(R.id.unknown_error_layout)
    RelativeLayout unknownErrorLayout;

    private Category category;
    private Country country;
    private Language language;
    private String q;
    private boolean isArticlesStackEmpty;
    private ArticleCardsContract.State state;

    private ArticleCardsAdapter articleCardsAdapter;

    private ArticleCardsContract.Presenter presenter;
    private Unbinder unbinder;

    public ArticleCardsFragment() {

    }

    public static ArticleCardsFragment newInstance(Category category, Country country) {
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_KEY, category.name());
        bundle.putString(COUNTRY_KEY, country.name());
        ArticleCardsFragment articleCardsFragment = new ArticleCardsFragment();
        articleCardsFragment.setArguments(bundle);
        return articleCardsFragment;
    }

    public static ArticleCardsFragment newInstance(Language language) {
        Bundle bundle = new Bundle();
        bundle.putString(LANGUAGE_KEY, language.name());
        ArticleCardsFragment articleCardsFragment = new ArticleCardsFragment();
        articleCardsFragment.setArguments(bundle);
        return articleCardsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_cards, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().containsKey(CATEGORY_KEY)) {
                category = Category.valueOf(getArguments().getString(CATEGORY_KEY, null));
            }
            if (getArguments().containsKey(LANGUAGE_KEY)) {
                language = Language.valueOf(getArguments().getString(LANGUAGE_KEY, null));
            }
            if (getArguments().containsKey(COUNTRY_KEY)) {
                country = Country.valueOf(getArguments().getString(COUNTRY_KEY, null));
            }
            q = getArguments().getString(Q_KEY, null);
        }
        if (savedInstanceState != null) {
            isArticlesStackEmpty = savedInstanceState.getBoolean(NO_MORE_ARTICLES_KEY, false);
            state = savedInstanceState.getParcelable(ArticleCardsState.ARTICLE_CARDS_STATE);
        }

        articleCardsAdapter = new ArticleCardsAdapter(getActivity(), 0);
        cardStackView.setAdapter(articleCardsAdapter);

        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                int index = cardStackView.getTopIndex() - 1;
                if (direction.equals(SwipeDirection.Bottom) && articleCardsAdapter.getCount() > index) {
                    presenter.saveArticle(articleCardsAdapter.getItem(index));
                    presenter.upsertFavoriteSourceSaved(DomainUtils.getDomainName(articleCardsAdapter.getItem(index).getUrl()));
                }
                if (cardStackView.getTopIndex() == articleCardsAdapter.getCount() - 9) {
                    presenter.getArticles();
                }
                if ((articleCardsAdapter.getCount() - cardStackView.getTopIndex()) == 0) {
                    isArticlesStackEmpty = true;
                    handleNoArticlesLayoutVisibility();
                }
                state = presenter.getState();
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void onCardClicked(int index) {
                presenter.upsertFavoriteSourceClicks(DomainUtils.getDomainName(articleCardsAdapter.getItem(index).getUrl()));
                ChromePagesHelper.openChromePageHelper(getActivity(), articleCardsAdapter.getItem(index).getUrl());
            }

            @Override
            public void onCardDragging(float percentX, float percentY) {
            }

            @Override
            public void onCardReversed() {
            }

            @Override
            public void onCardMovedToOrigin() {
            }
        });

        presenter = new ArticleCardsPresenter(this, ((TopicalityApplication) getActivity().getApplication()).getRequestFactory(),
                ((TopicalityApplication) getActivity().getApplication()).getDatabase(), category, country, language, q);
        presenter.subscribe(state);
        if (isArticlesStackEmpty) {
            progressBar.setVisibility(View.GONE);
        }
        handleNoArticlesLayoutVisibility();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(NO_MORE_ARTICLES_KEY, isArticlesStackEmpty);
        outState.putParcelable(ArticleCardsState.ARTICLE_CARDS_STATE, state);
    }

    @Override
    public void addArticles(List<DatabaseArticle> articles) {
        cardStackView.setPaginationReserved();
        articleCardsAdapter.addAll(articles);
        articleCardsAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public List<DatabaseArticle> extractRemainingArticles() {
        List<DatabaseArticle> articles = new ArrayList<>();
        if (cardStackView != null && articleCardsAdapter != null) {
            for (int i = cardStackView.getTopIndex(); i < articleCardsAdapter.getCount(); i++) {
                articles.add(articleCardsAdapter.getItem(i));
            }
        }
        return articles;
    }

    @Override
    public void showNetworkError() {
        progressBar.setVisibility(View.GONE);
        networkErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnknownError() {
        progressBar.setVisibility(View.GONE);
        unknownErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public NewsType getTypeOfNews() {
        return language == null ? NewsType.TOP : NewsType.EVERYTING;
    }


    private void handleNoArticlesLayoutVisibility() {
        if (isArticlesStackEmpty && networkErrorLayout.getVisibility() == View.GONE && unknownErrorLayout.getVisibility() == View.GONE) {
            noMoreArticlesLayout.setVisibility(View.VISIBLE);
        } else if (!isArticlesStackEmpty) {
            noMoreArticlesLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.button_retry_articles)
    void onRetryArticlesButtonClick() {
        noMoreArticlesLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        refresh();
    }

    @OnClick(R.id.button_retry_network_error)
    void onRetryNetworkErrorButtonClick() {
        networkErrorLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        refresh();
    }

    @OnClick(R.id.button_retry_unknown_error)
    void onRetryUnknownErrorButtonClick() {
        unknownErrorLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        refresh();
    }

    private void refresh() {
        isArticlesStackEmpty = false;
        state = null;
        presenter.unsubscribe();
        onViewCreated(getView(), null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        articleCardsAdapter = null;
        presenter = null;
        state = null;
    }
}
