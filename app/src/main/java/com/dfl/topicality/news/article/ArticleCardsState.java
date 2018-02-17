package com.dfl.topicality.news.article;

import android.os.Parcel;
import android.os.Parcelable;

import com.dfl.topicality.database.DatabaseArticle;

import java.util.List;

/**
 * Created by loureiro on 30-01-2018.
 */

public class ArticleCardsState implements ArticleCardsContract.State, Parcelable {

    static final String ARTICLE_CARDS_STATE = "ARTICLE_CARDS_STATE";

    private int page;
    private List<DatabaseArticle> databaseArticleList;

    ArticleCardsState(int page, List<DatabaseArticle> databaseArticleList) {
        this.page = page;
        this.databaseArticleList = databaseArticleList;
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

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public List<DatabaseArticle> getRemainingArticles() {
        return databaseArticleList;
    }
}
