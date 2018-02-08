package com.dfl.topicality.saved;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.dfl.topicality.R;
import com.dfl.topicality.database.DatabaseArticle;

import java.util.List;

/**
 * Created by loureiro on 31-01-2018.
 */

public class SavedArticlesAdapter extends RecyclerView.Adapter<SavedArticleViewHolder> {

    private final List<DatabaseArticle> databaseArticleList;
    private RequestManager requestManager;

    SavedArticlesAdapter(List<DatabaseArticle> databaseArticleList, RequestManager requestManager){
        this.databaseArticleList = databaseArticleList;
        this.requestManager = requestManager;
    }

    @Override
    public SavedArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SavedArticleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SavedArticleViewHolder holder, int position) {
        DatabaseArticle databaseArticle = databaseArticleList.get(position);
        holder.setImage(requestManager,databaseArticle.getUrlToImage());
        holder.setTitle(databaseArticle.getTitle());
        holder.setSourceName(databaseArticle.getSourceName());
        holder.setUrl(databaseArticle.getUrl());
    }

    @Override
    public int getItemCount() {
        return databaseArticleList != null ? databaseArticleList.size() : 0;
    }

    void addDatabaseArticle(DatabaseArticle databaseArticle) {
        databaseArticleList.add(databaseArticle);
        notifyItemInserted(getItemCount());
    }

    void deleteDatabaseArticle(int index){
        databaseArticleList.remove(index);
        notifyItemRemoved(index);
    }
}
