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

    private final int page;
    private final String domains;
    private List<DatabaseArticle> databaseArticleList;

    ArticleCardsState(int page, String domains, List<DatabaseArticle> databaseArticleList) {
        this.page = page;
        this.domains = domains;
        this.databaseArticleList = databaseArticleList;
    }

    private ArticleCardsState(Parcel in) {
        page = in.readInt();
        domains = in.readString();
        in.readList(databaseArticleList, null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeString(domains);
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
    public String getDomains() {
        return domains;
    }

    @Override
    public List<DatabaseArticle> getRemainingArticles() {
        return databaseArticleList;
    }
}
