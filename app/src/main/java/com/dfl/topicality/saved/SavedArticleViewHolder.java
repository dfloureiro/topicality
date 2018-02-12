package com.dfl.topicality.saved;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dfl.topicality.ChromePagesHelper;
import com.dfl.topicality.ImageLoader;
import com.dfl.topicality.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    SavedArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(v -> ChromePagesHelper.openChromePageHelper(itemView.getContext(), getUrl()));
    }

    public void setImage(ImageLoader imageLoader, String urlToImage) {
        imageLoader.loadImageIntoImageViewWithProgressBar(urlToImage,progressBar,image);
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
