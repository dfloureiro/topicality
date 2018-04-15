package com.dfl.topicality.news.article;

import android.os.Parcel;
import android.os.Parcelable;

import com.dfl.topicality.database.DatabaseArticle;

import java.util.List;

/**
 * Created by loureiro on 30-01-2018.
 */

public class ArticleCardsState implements Parcelable {

    public static final String ARTICLE_CARDS_STATE = "ARTICLE_CARDS_STATE";

    private final int page;
    private List<DatabaseArticle> databaseArticleList;

    public ArticleCardsState(int page) {
        this.page = page;
    }

    private ArticleCardsState(Parcel in) {
        page = in.readInt();
        in.readList(databaseArticleList, null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeList(databaseArticleList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleCardsState> CREATOR = new Creator<ArticleCardsState>() {
        @Override
        public ArticleCardsState createFromParcel(Parcel in) {
            return new ArticleCardsState(in);
        }

        @Override
        public ArticleCardsState[] newArray(int size) {
            return new ArticleCardsState[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setRemainingArticles(List<DatabaseArticle> databaseArticleList) {
        this.databaseArticleList = databaseArticleList;
    }

    public List<DatabaseArticle> getRemainingArticles() {
        return databaseArticleList;
    }
}
