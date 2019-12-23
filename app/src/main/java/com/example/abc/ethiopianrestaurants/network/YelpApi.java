package com.example.abc.ethiopianrestaurants.network;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApi {

    @GET("/v3/businesses/search")
    Single<GetBusinessesResponse> getNearbyBusinesses(
        @Query("term") String term,
        @Query("latitude") double latitude,
        @Query("longitude") double longitude);

    @GET("/v3/businesses/search")
    Single<GetBusinessesResponse> getNearbyBusinesses(
        @Query("term") String term,
        @Query("latitude") double latitude,
        @Query("longitude") double longitude,
        @Query("limit") int limit);

    @GET("/v3/businesses/{id}")
    Single<Business> getBusinessDetails(@Path("id") String businessId);

    @GET("/v3/businesses/{id}/reviews")
    Single<GetReviewsResponse> getBusinessReviews(@Path("id") String businessId);
}
