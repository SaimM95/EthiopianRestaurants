package com.example.abc.ethiopianrestaurants.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

import java.util.List;

public class Business {

    @SerializedName("id") public final String id;
    @SerializedName("name") public final String name;
    @SerializedName("image_url") public final String imageUrl;
    @SerializedName("review_count") public final int reviewCount;
    @SerializedName("rating") public final double rating;
    @SerializedName("distance") public final double distance;
    @SerializedName("location") public final Location location;

    @SerializedName("photos")
    @Nullable
    public final List<String> photos;

    public Business(String id, String name, String imageUrl, int reviewCount, double rating,
        double distance, Location location, @Nullable List<String> photos) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.distance = distance;
        this.location = location;
        this.photos = photos;
    }

    static class Location {

        @SerializedName("address1") public final String address;
        @SerializedName("city") public final String city;
        @SerializedName("zip_code") public final String zipCode;
        @SerializedName("country") public final String country;
        @SerializedName("state") public final String state;

        public Location(String address, String city, String zipCode, String country, String state) {
            this.address = address;
            this.city = city;
            this.zipCode = zipCode;
            this.country = country;
            this.state = state;
        }
    }

}
