package com.example.abc.ethiopianrestaurants.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id") public final String id;
    @SerializedName("text") public final String text;
    @SerializedName("rating") public final double rating;
    @SerializedName("time_created") public final String timeCreated;
    @SerializedName("user") public final User user;

    public Review(String id, String text, double rating, String timeCreated,
        User user) {
        this.id = id;
        this.text = text;
        this.rating = rating;
        this.timeCreated = timeCreated;
        this.user = user;
    }
}
