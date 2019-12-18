package com.example.abc.ethiopianrestaurants.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetReviewsResponse {

    @SerializedName("reviews") public final List<Review> reviews;
    @SerializedName("total") public final int total;

    public GetReviewsResponse(List<Review> reviews, int total) {
        this.reviews = reviews;
        this.total = total;
    }
}
