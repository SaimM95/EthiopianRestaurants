package com.example.abc.ethiopianrestaurants.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBusinessesResponse {

    @SerializedName("businesses") public final List<Business> businesses;
    @SerializedName("total") public final int total;

    public GetBusinessesResponse(List<Business> businesses, int total) {
        this.businesses = businesses;
        this.total = total;
    }
}
