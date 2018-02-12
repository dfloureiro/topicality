package com.dfl.topicality.news.article;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dfl.topicality.ChromePagesHelper;
import com.dfl.topicality.R;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.datamodel.Article;
import com.dfl.topicality.network.RequestFactory;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import org.parceler.Parcels;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsFragment extends Fragment implements ArticleCardsContract.View {

    private final static String CATEGORY_KEY = "CATEGORY_KEY";
    private final static String COUNTRY_KEY = "COUNTRY_KEY";
    private final static String SOURCES_KEY = "SOURCES_KEY";
    private final static String Q_KEY = "Q_KEY";

    @BindView(R.id.main_fragment_card_stack_view)
    CardStackView cardStackView;
    @BindView(R.id.main_fragment_progress_bar)
    ProgressBar progressBar;

    private String category;
    private String country;
    private String sources;
    private String q;

    private ArticleCardsAdapter articleCardsAdapter;

    private ArticleCardsContract.Presenter presenter;
    private Unbinder unbinder;

    public ArticleCardsFragment() {

    }

    public static ArticleCardsFragment newInstance(String category, String country, String sources, String q) {
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_KEY, category);
        bundle.putString(COUNTRY_KEY, country);
        bundle.putString(SOURCES_KEY, sources);
        bundle.putString(Q_KEY, q);
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
            category = getArguments().getString(CATEGORY_KEY, null);
            country = getArguments().getString(COUNTRY_KEY, null);
            sources = getArguments().getString(SOURCES_KEY, null);
            q = getArguments().getString(Q_KEY, null);
        }

        articleCardsAdapter = new ArticleCardsAdapter(getActivity(), 0);
        cardStackView.setAdapter(articleCardsAdapter);

        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                int index = cardStackView.getTopIndex()-1;
                if(direction.equals(SwipeDirection.Bottom) && articleCardsAdapter.getCount()>index){
                    presenter.saveArticle(articleCardsAdapter.getItem(index));
                }
                if (cardStackView.getTopIndex() == articleCardsAdapter.getCount() - 9) {
                    presenter.getTopHeadlineArticles();
                }
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void onCardClicked(int index) {
                ChromePagesHelper.openChromePageHelper(getActivity(),articleCardsAdapter.getItem(index).getUrl());
            }

            @Override
            public void onCardDragging(float percentX, float percentY) {}

            @Override
            public void onCardReversed(){}

            @Override
            public void onCardMovedToOrigin() {}
        });

        presenter = new ArticleCardsPresenter(this, new RequestFactory(), ((TopicalityApplication) getActivity().getApplication()).getDatabase(), category, country, sources, q);
        presenter.subscribe(savedInstanceState != null ? Parcels.unwrap(
                savedInstanceState.getParcelable(ArticleCardsState.ARTICLE_CARDS_STATE)) : null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            outState.putParcelable(ArticleCardsState.ARTICLE_CARDS_STATE, Parcels.wrap(presenter.getState()));
        }
    }

    @Override
    public void addArticles(List<Article> articles) {
        cardStackView.setPaginationReserved();
        articleCardsAdapter.addAll(articles);
        articleCardsAdapter.notifyDataSetChanged();
        cardStackView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public LinkedList<Article> extractRemainingArticles() {
        LinkedList<Article> articles = new LinkedList<>();
        if (cardStackView != null && articleCardsAdapter != null) {
            for (int i = cardStackView.getTopIndex(); i < articleCardsAdapter.getCount(); i++) {
                articles.add(articleCardsAdapter.getItem(i));
            }
        }
        return articles;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
