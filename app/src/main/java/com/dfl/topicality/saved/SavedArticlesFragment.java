package com.dfl.topicality.saved;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dfl.topicality.R;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.database.DatabaseArticle;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.annotation.VisibleForTesting.PROTECTED;

/**
 * Created by loureiro on 30-01-2018.
 */

public class SavedArticlesFragment extends Fragment implements SavedArticlesContract.View {

    @BindView(R.id.saved_recycler_view_layout)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_saved_layout)
    LinearLayout container;
    @BindView(R.id.no_saved_bookmarks_layout)
    RelativeLayout noSavedBookmarksLayout;

    @Inject
    @VisibleForTesting(otherwise = PROTECTED)
    public
    SavedArticlesPresenter presenter;
    @Inject
    @VisibleForTesting(otherwise = PROTECTED)
    public
    SavedArticlesAdapter savedArticlesAdapter;

    private Unbinder unbinder;

    public static SavedArticlesFragment newInstance() {
        return new SavedArticlesFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopicalityApplication.get(Objects.requireNonNull(getActivity())).getComponentsFactory().getSavedArticlesComponent(this).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(savedArticlesAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                presenter.removeFromSaved(((SavedArticleViewHolder) viewHolder).getUrl(), viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        presenter.subscribe(null);
    }

    @Override
    public void addArticle(DatabaseArticle databaseArticle) {
        savedArticlesAdapter.addDatabaseArticle(databaseArticle);
    }

    @Override
    public void removeArticle(int viewHolderPosition) {
        savedArticlesAdapter.deleteDatabaseArticle(viewHolderPosition);
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideNoBookmarksLayout() {
        recyclerView.setVisibility(View.VISIBLE);
        noSavedBookmarksLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoBookmarksLayout() {
        recyclerView.setVisibility(View.GONE);
        noSavedBookmarksLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
