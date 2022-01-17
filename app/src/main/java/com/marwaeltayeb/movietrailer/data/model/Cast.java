package com.marwaeltayeb.movietrailer.data.model;

import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("name")
    private String nameOfActor;

    @SerializedName("character")
    private String character;

    @SerializedName("profile_path")
    private String profileOfActor;

    public Cast(String nameOfActor, String character, String profileOfActor) {
        this.nameOfActor = nameOfActor;
        this.character = character;
        this.profileOfActor = profileOfActor;
    }

    public String getNameOfActor() {
        return nameOfActor;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfileOfActor() {
        return profileOfActor;
    }
}
