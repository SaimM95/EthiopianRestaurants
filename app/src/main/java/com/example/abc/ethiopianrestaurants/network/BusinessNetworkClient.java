package com.example.abc.ethiopianrestaurants.network;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;

public interface BusinessNetworkClient {

    void getBusinesses(String searchTerm, double longitude, double latitude, int limit,
        NetworkCallback<GetBusinessesResponse> callback);

    void getBusinessDetails(String businessId, NetworkCallback<Business> callback);

    void getBusinessReviews(String businessId, NetworkCallback<GetReviewsResponse> callback);
}
