package com.example.abc.ethiopianrestaurants.network;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApi {

    @GET("/businesses/search")
    Call<GetBusinessesResponse> getNearbyBusinesses(
        @Query("term") String term,
        @Query("latitude") double latitude,
        @Query("longitude") double longitude,
        @Query("limit") int limit);

    @GET("/businesses/{id}")
    Call<Business> getBusinessDetails(@Path("id") String businessId);

    @GET("/businesses/{id}/reviews")
    Call<GetReviewsResponse> getBusinessReviews(@Path("id") String businessId);
}
