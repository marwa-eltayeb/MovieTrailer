package com.marwaeltayeb.movietrailer.data.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    private String id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return getId().equals(review.getId()) &&
                getAuthor().equals(review.getAuthor()) &&
                getContent().equals(review.getContent()) &&
                getUrl().equals(review.getUrl());
    }
}
