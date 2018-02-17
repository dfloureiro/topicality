package com.dfl.topicality;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import io.reactivex.annotations.Nullable;

/**
 * Created by loureiro on 12-02-2018.
 */

public class ImageLoader {

    private RequestManager requestManager;

    /**
     * @param context Should pass the current context, not the application context.
     */
    public ImageLoader(Context context) {
        this.requestManager = Glide.with(context);
    }

    public void loadImageIntoImageViewWithProgressBar(String urlToImage, ProgressBar progressBar, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        requestManager.load(urlToImage).apply(options.centerCrop()).listener(new RequestListener<Drawable>() {
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
        }).into(imageView);
    }
}
