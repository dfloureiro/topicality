package com.dfl.topicality;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by loureiro on 12-02-2018.
 */

public class ImageLoader {

    private final RequestManager requestManager;

    /**
     * @param context Should pass the current context, not the application context.
     */
    public ImageLoader(Context context) {
        this.requestManager = Glide.with(context);
    }

    public void loadImageIntoImageViewWithProgressBar(String urlToImage, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        if (urlToImage != null) {
            urlToImage = urlToImage.trim();
        }
        requestManager.load(urlToImage).apply(options.centerCrop().placeholder(R.mipmap.ic_launcher_foreground)).into(imageView);
    }
}
