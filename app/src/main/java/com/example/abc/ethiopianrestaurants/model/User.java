package com.example.abc.ethiopianrestaurants.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id") public final String id;
    @SerializedName("image_url") public final String imageUrl;
    @SerializedName("name") public final String name;

    public User(String id, String imageUrl, String name) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
