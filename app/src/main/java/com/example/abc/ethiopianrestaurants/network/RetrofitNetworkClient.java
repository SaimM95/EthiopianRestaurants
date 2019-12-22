package com.example.abc.ethiopianrestaurants.network;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;

import io.reactivex.Single;

public class RetrofitNetworkClient implements BusinessNetworkClient {

    private final YelpApi yelpApi;

    RetrofitNetworkClient() {
        yelpApi = RetrofitClient.getInstance().getRetrofit().create(YelpApi.class);
    }

    @Override
    public Single<GetBusinessesResponse> getBusinesses(String searchTerm, double latitude,
        double longitude, int limit) {
        return yelpApi.getNearbyBusinesses(searchTerm, latitude, longitude, limit);
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
