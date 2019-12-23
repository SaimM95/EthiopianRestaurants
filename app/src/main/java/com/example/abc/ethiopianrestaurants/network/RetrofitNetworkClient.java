package com.example.abc.ethiopianrestaurants.network;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;

import io.reactivex.Single;

public class RetrofitNetworkClient implements BusinessNetworkClient {

    // TODO
    // Lat/Long for Toronto
    private static final double LATITUDE = 43.651070;
    private static final double LONGITUDE = -79.347015;

    private final YelpApi yelpApi;

    RetrofitNetworkClient() {
        yelpApi = RetrofitClient.getInstance().getRetrofit().create(YelpApi.class);
    }

    @Override
    public Single<GetBusinessesResponse> getBusinesses(String searchTerm) {
        return yelpApi.getNearbyBusinesses(searchTerm, LATITUDE, LONGITUDE);
    }

    @Override
    public Single<GetBusinessesResponse> getBusinesses(String searchTerm, int limit) {
        return yelpApi.getNearbyBusinesses(searchTerm, LATITUDE, LONGITUDE, limit);
    }

    @Override
    public Single<Business> getBusinessDetails(String businessId) {
        return yelpApi.getBusinessDetails(businessId);
    }

    @Override
    public Single<GetReviewsResponse> getBusinessReviews(String businessId) {
        return yelpApi.getBusinessReviews(businessId);
    }
}
