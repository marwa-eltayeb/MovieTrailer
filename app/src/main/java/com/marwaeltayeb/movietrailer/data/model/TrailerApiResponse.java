package com.marwaeltayeb.movietrailer.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerApiResponse {

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<Trailer> trailers;

    public Integer getId() {
        return id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
