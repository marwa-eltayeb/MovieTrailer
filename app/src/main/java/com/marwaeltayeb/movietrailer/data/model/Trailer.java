package com.marwaeltayeb.movietrailer.data.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trailer)) return false;
        Trailer trailer = (Trailer) o;
        return getKeyOfTrailer().equals(trailer.getKeyOfTrailer()) &&
                getNameOfTrailer().equals(trailer.getNameOfTrailer());
    }
}
