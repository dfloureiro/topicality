package com.dfl.topicality.news.article;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dfl.topicality.R;
import com.dfl.topicality.database.DatabaseArticle;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsFragment extends Fragment {

    public final static String LINEAR_LAYOUT_MANAGER_KEY = "LINEAR_LAYOUT_MANAGER_KEY";
    public final static String Q_KEY = "Q_KEY";

    @BindView(R.id.articles_recycler_view_layout)
    RecyclerView recyclerView;
    @BindView(R.id.main_fragment_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_news_cards_layout)
    ConstraintLayout container;
    @BindView(R.id.network_error_layout)
    RelativeLayout networkErrorLayout;
    @BindView(R.id.unknown_error_layout)
    RelativeLayout unknownErrorLayout;

    @Inject
    ArticleCardsPresenterInterface articleCardsPresenterInterface;
    @Inject
    ArticleCardsAdapter articleCardsAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_cards, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        if (savedInstanceState != null) {
            linearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(LINEAR_LAYOUT_MANAGER_KEY));
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(articleCardsAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (progressBar.getVisibility() == View.GONE) {
                        if ((visibleItemCount + visibleItemCount / 2 + pastVisiblesItems) >= totalItemCount) {
                            showProgressBar();
                            articleCardsPresenterInterface.getNextArticles();
                        }
                    }
                }
            }
        });

        articleCardsPresenterInterface.subscribe(savedInstanceState != null ? savedInstanceState.getParcelable(ArticleCardsState.ARTICLE_CARDS_STATE) : null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        ArticleCardsState state = articleCardsPresenterInterface.getState();
        state.setRemainingArticles(articleCardsAdapter.getDatabaseArticleList());
        outState.putParcelable(ArticleCardsState.ARTICLE_CARDS_STATE, state);
        if (recyclerView != null) {
            outState.putParcelable(LINEAR_LAYOUT_MANAGER_KEY, recyclerView.getLayoutManager().onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

    public void addArticle(DatabaseArticle databaseArticle) {
        articleCardsAdapter.addDatabaseArticle(databaseArticle);
        hideProgressbar();
    }

    private void refresh() {
        showProgressBar();
        articleCardsAdapter.deleteAllDatabaseArticle();
        articleCardsPresenterInterface.refresh();
    }

    public void showNetworkError() {
        hideProgressbar();
        networkErrorLayout.setVisibility(View.VISIBLE);
    }

    public void showUnknownError() {
        hideProgressbar();
        unknownErrorLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_retry_network_error)
    void onRetryNetworkErrorButtonClick() {
        networkErrorLayout.setVisibility(View.GONE);
        refresh();
    }

    @OnClick(R.id.button_retry_unknown_error)
    void onRetryUnknownErrorButtonClick() {
        unknownErrorLayout.setVisibility(View.GONE);
        refresh();
    }

    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        articleCardsPresenterInterface.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
