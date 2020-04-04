package com.example.newsarticleapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsArticleModel implements Parcelable {
    String source,author,title,description,url,urlToImage,publishedAt,content;

    public NewsArticleModel(String source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    protected NewsArticleModel(Parcel in) {
        source = in.readString();
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        content = in.readString();
    }

    public static final Creator<NewsArticleModel> CREATOR = new Creator<NewsArticleModel>() {
        @Override
        public NewsArticleModel createFromParcel(Parcel in) {
            return new NewsArticleModel(in);
        }

        @Override
        public NewsArticleModel[] newArray(int size) {
            return new NewsArticleModel[size];
        }
    };

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(source);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
        dest.writeString(content);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof NewsArticleModel){
            NewsArticleModel p = (NewsArticleModel) o;
            return this.url.equals(p.getUrl());
        } else
            return false;
    }
}
