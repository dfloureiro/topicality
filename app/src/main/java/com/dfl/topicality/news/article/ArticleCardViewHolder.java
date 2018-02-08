package com.dfl.topicality.news.article;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dfl.topicality.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardViewHolder {

    @BindView(R.id.text_view_title)
    TextView title;
    @BindView(R.id.image_view_card)
    ImageView urlToImage;
    @BindView(R.id.card_progress_bar)
    ProgressBar progressBar;

    ArticleCardViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    void setTitle(String title) {
        this.title.setText(title);
    }

    void setUrlToImage(Context context, String urlToImage) {
        RequestOptions options = new RequestOptions();
        Glide.with(context).load(urlToImage).apply(options.centerCrop()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                return false;
            }
        }).into(this.urlToImage);
    }
}
