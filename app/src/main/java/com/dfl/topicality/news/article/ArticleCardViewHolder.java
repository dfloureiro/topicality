package com.dfl.topicality.news.article;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.R;

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

    public ArticleCardViewHolder(View itemView, ArticleCardsPresenterInterface articleCardsPresenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void setTitle(String title) {
        this.title.setText(title);
    }

    void setDescription(String description) {
        this.description.setText(description);
    }

    void setUrlToImage(ImageLoader imageLoader, String urlToImage) {
        imageLoader.loadImageIntoImageViewWithProgressBar(urlToImage, imageViewCard);
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
