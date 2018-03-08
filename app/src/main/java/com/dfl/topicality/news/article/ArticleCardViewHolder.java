package com.dfl.topicality.news.article;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by loureiro on 29-01-2018.
 */

class ArticleCardViewHolder {

    @BindView(R.id.text_view_title)
    TextView title;
    @BindView(R.id.image_view_card)
    ImageView imageViewCard;
    @BindView(R.id.image_view_viewed)
    ImageView isViewedIcon;
    @BindView(R.id.card_progress_bar)
    ProgressBar progressBar;

    ArticleCardViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    void setTitle(String title) {
        this.title.setText(title);
    }

    void setUrlToImage(ImageLoader imageLoader, String urlToImage) {
        imageLoader.loadImageIntoImageViewWithProgressBar(urlToImage, progressBar, imageViewCard);
    }

    void setIsViewedIcon(boolean isViewedArticle) {
        if (isViewedArticle) {
            isViewedIcon.setVisibility(View.VISIBLE);
        } else {
            isViewedIcon.setVisibility(View.GONE);
        }
    }

    void setIsViewedIconColor(int color) {
        isViewedIcon.setColorFilter(color);
    }
}
