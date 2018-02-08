package com.dfl.topicality.news.article;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dfl.topicality.R;
import com.dfl.topicality.datamodel.Article;

/**
 * Created by loureiro on 29-01-2018.
 */

public class ArticleCardsAdapter extends ArrayAdapter<Article> {

    ArticleCardsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View contentView, @NonNull ViewGroup parent) {
        ArticleCardViewHolder articleCardViewHolder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.card_content, parent, false);
            articleCardViewHolder = new ArticleCardViewHolder(contentView);
            contentView.setTag(articleCardViewHolder);
        } else {
            articleCardViewHolder = (ArticleCardViewHolder) contentView.getTag();
        }

        Article article = getItem(position);

        if (article != null) {
            articleCardViewHolder.setTitle(article.getTitle());
            articleCardViewHolder.setUrlToImage(getContext(), article.getUrlToImage());
        }

        return contentView;
    }


}