package com.marwaeltayeb.movietrailer.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastApiResponse {

    @SerializedName("id")
    private Integer id;
    @SerializedName("cast")
    private List<Cast> cast;

    public Integer getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }
}
