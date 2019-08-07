package com.marwaeltayeb.movietrailer.models;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
