package com.dfl.topicality.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by loureiro on 31-01-2018.
 */

@Entity
public class DatabaseArticle implements Parcelable {

    @NonNull
    @PrimaryKey
    private String url;
    @ColumnInfo(name = "source_name")
    private String sourceName;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "image_url")
    private String urlToImage;
    @ColumnInfo(name = "date")
    private String publishedAt;
    @ColumnInfo(name = "is_favourite")
    private int isFavourite;
    @ColumnInfo(name = "is_viewed")
    private int isViewed;
    @ColumnInfo(name = "is_clicked")
    private int isClicked;


    public DatabaseArticle(@NonNull String url, String sourceName, String author, String title, String description, String urlToImage, String publishedAt) {
        this.url = url;
        this.sourceName = sourceName;
        this.author = author;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.isFavourite = 0;
        this.isViewed = 0;
        this.isClicked = 0;
    }

    private DatabaseArticle(Parcel in) {
        url = in.readString();
        sourceName = in.readString();
        author = in.readString();
        title = in.readString();
        description = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        isFavourite = in.readInt();
        isViewed = in.readInt();
        isClicked = in.readInt();
    }

    public static final Creator<DatabaseArticle> CREATOR = new Creator<DatabaseArticle>() {
        @Override
        public DatabaseArticle createFromParcel(Parcel in) {
            return new DatabaseArticle(in);
        }

        @Override
        public DatabaseArticle[] newArray(int size) {
            return new DatabaseArticle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(sourceName);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
        dest.writeInt(isFavourite);
        dest.writeInt(isViewed);
        dest.writeInt(isClicked);
    }


    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(int isViewed) {
        this.isViewed = isViewed;
    }

    public int getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(int isClicked) {
        this.isClicked = isClicked;
    }
}
