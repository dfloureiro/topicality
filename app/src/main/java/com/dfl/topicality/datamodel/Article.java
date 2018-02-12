package com.dfl.topicality.datamodel;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class Article {

    @SerializedName("source")
    @Expose
    Source source;
    @SerializedName("author")
    @Expose
    String author;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("url")
    @Expose
    @NonNull
    String url;
    @SerializedName("urlToImage")
    @Expose
    String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    String publishedAt;

    @ParcelConstructor
    public Article(Source source, String author, String title, String description, @NonNull String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
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

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
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

}
