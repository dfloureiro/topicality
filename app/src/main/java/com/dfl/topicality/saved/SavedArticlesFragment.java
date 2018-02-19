package com.dfl.topicality.saved;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.R;
import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.database.DatabaseArticle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by loureiro on 30-01-2018.
 */

public class SavedArticlesFragment extends Fragment implements SavedArticlesContract.View {

    @BindView(R.id.recycler_view_layout)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_saved_layout)
    LinearLayout container;

    private SavedArticlesAdapter savedArticlesAdapter;
    private SavedArticlesContract.Presenter presenter;
    private ImageLoader imageLoader;

    private Unbinder unbinder;

    public SavedArticlesFragment() {

    }

    public static SavedArticlesFragment newInstance() {
        return new SavedArticlesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        imageLoader = new ImageLoader(getActivity());
        presenter = new SavedArticlesPresenter(this, ((TopicalityApplication) getActivity().getApplication()).getDatabase());
        recyclerView.setLayoutManager(layoutManager);
        savedArticlesAdapter = new SavedArticlesAdapter(new ArrayList<>(0), imageLoader, presenter);
        recyclerView.setAdapter(savedArticlesAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                presenter.deleteArticle(((SavedArticleViewHolder) viewHolder).getUrl(), viewHolder.getAdapterPosition());
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unsubscribe();
        }
        savedArticlesAdapter = null;
        presenter = null;
        imageLoader = null;
    }
}
