package com.marwaeltayeb.movietrailer.models;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("name")
    private String nameOfTrailer;

    @SerializedName("key")
    private String keyOfTrailer;

    public Trailer(String nameOfTrailer, String keyOfTrailer) {
        this.nameOfTrailer = nameOfTrailer;
        this.keyOfTrailer = keyOfTrailer;
    }

    public String getNameOfTrailer() {
        return nameOfTrailer;
    }

    public String getKeyOfTrailer() {
        return keyOfTrailer;
    }
}
