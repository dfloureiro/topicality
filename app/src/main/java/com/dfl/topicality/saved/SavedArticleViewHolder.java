package com.dfl.topicality.saved;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dfl.topicality.ChromePagesHelper;
import com.dfl.topicality.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by loureiro on 31-01-2018.
 */

public class SavedArticleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.saved_item_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.saved_item_image_view)
    ImageView image;
    @BindView(R.id.saved_item_title)
    TextView title;
    @BindView(R.id.saved_item_source_name)
    TextView sourceName;

    private String url;

    public SavedArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(v -> ChromePagesHelper.openChromePageHelper(itemView.getContext(),getUrl()));
    }

    public void setImage(RequestManager requestManager, String urlToImage) {
        requestManager.load(urlToImage).listener(new RequestListener<Drawable>() {
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
        }).into(this.image);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSourceName(String sourceName) {
        this.sourceName.setText(sourceName);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
