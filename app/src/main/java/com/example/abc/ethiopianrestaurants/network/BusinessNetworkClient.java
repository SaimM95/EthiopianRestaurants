package com.example.abc.ethiopianrestaurants.network;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;

import io.reactivex.Single;

public interface BusinessNetworkClient {

    Single<GetBusinessesResponse> getBusinesses(String searchTerm);

    Single<GetBusinessesResponse> getBusinesses(String searchTerm, int limit);

    Single<Business> getBusinessDetails(String businessId);

    Single<GetReviewsResponse> getBusinessReviews(String businessId);
}
