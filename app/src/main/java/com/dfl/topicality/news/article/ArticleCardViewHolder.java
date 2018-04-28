package com.dfl.topicality.news.article;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfl.topicality.ChromePagesHelper;
import com.dfl.topicality.DomainUtils;
import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.R;
import com.dfl.topicality.database.DatabaseArticle;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by loureiro on 29-01-2018.
 */

class ArticleCardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.card_item_title)
    TextView title;
    @BindView(R.id.card_item_description)
    TextView description;
    @BindView(R.id.card_item_image_view)
    ImageView imageViewCard;
    /*@BindView(R.id.image_view_viewed)
    ImageView isViewedIcon;*/
    private DatabaseArticle databaseArticle;
    private String url;

    public ArticleCardViewHolder(View itemView, ArticleCardsPresenterInterface presenter) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            setAsViewed();
            presenter.upsertFavoriteSourceClicks(DomainUtils.getDomainName(getUrl()));
            presenter.setArticleAsClicked(databaseArticle);
            ChromePagesHelper.openChromePageHelper(itemView.getContext(), getUrl());
        });
        ButterKnife.bind(this, itemView);
    }

    void setAsViewed() {
        title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.viewedText));
        description.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.viewedText));
    }

    void setTitle(String title) {
        this.title.setText(title);
        this.title.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.black));
    }

    void setDescription(String description) {
        this.description.setText(description);
        this.description.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.black));
    }

    void setUrlToImage(ImageLoader imageLoader, String urlToImage) {
        imageLoader.loadImageIntoImageViewWithProgressBar(urlToImage, imageViewCard);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDatabaseArticle(DatabaseArticle databaseArticle) {
        this.databaseArticle = databaseArticle;
    }

    /*void setIsViewedIcon(boolean isViewedArticle) {
        if (isViewedArticle) {
            isViewedIcon.setVisibility(View.VISIBLE);
        } else {
            isViewedIcon.setVisibility(View.GONE);
        }
    }

    void setIsViewedIconColor(int color) {
        isViewedIcon.setColorFilter(color);
    }*/
}
