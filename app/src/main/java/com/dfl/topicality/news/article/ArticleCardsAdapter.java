package com.dfl.topicality.news.article;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.R;
import com.dfl.topicality.database.DatabaseArticle;

import java.util.List;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsAdapter extends RecyclerView.Adapter<ArticleCardViewHolder> {

    private final List<DatabaseArticle> databaseArticleList;
    private final ImageLoader imageLoader;
    private final ArticleCardsPresenterInterface presenter;

    public ArticleCardsAdapter(List<DatabaseArticle> databaseArticleList, ImageLoader imageLoader, ArticleCardsPresenterInterface presenter) {
        this.databaseArticleList = databaseArticleList;
        this.imageLoader = imageLoader;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ArticleCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleCardViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_item, parent, false), presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleCardViewHolder holder, int position) {
        DatabaseArticle databaseArticle = databaseArticleList.get(position);
        holder.setUrl(databaseArticle.getUrl());
        holder.setDatabaseArticle(databaseArticle);
        holder.setUrlToImage(imageLoader, databaseArticle.getUrlToImage());
        holder.setTitle(databaseArticle.getTitle());
        holder.setDescription(databaseArticle.getDescription());
        if (databaseArticle.getIsClicked() == 1) {
            holder.setAsViewed();
        }
    }

    @Override
    public int getItemCount() {
        return databaseArticleList != null ? databaseArticleList.size() : 0;
    }

    public void addDatabaseArticle(DatabaseArticle databaseArticle) {
        databaseArticleList.add(databaseArticle);
        notifyItemInserted(getItemCount());
    }

    public void deleteAllDatabaseArticle() {
        int size = getItemCount();
        databaseArticleList.clear();
        notifyItemRangeChanged(0, size);
    }

    public List<DatabaseArticle> getDatabaseArticleList() {
        return databaseArticleList;
    }
}
