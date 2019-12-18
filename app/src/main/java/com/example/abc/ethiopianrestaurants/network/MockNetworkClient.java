package com.example.abc.ethiopianrestaurants.network;

import android.content.Context;
import android.util.Log;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MockNetworkClient implements BusinessNetworkClient {

    private static final String TAG = MockNetworkClient.class.getCanonicalName();

    private final Context context;

    MockNetworkClient(Context context) {
        this.context = context;
    }

    @Override
    public void getBusinesses(String searchTerm, double longitude, double latitude, int limit,
        NetworkCallback<GetBusinessesResponse> callback) {
        createMockResponse("businesses.json", callback, GetBusinessesResponse.class);
    }

    @Override
    public void getBusinessDetails(String businessId, NetworkCallback<Business> callback) {
        createMockResponse("business-details.json", callback, Business.class);
    }

    @Override
    public void getBusinessReviews(String businessId,
        NetworkCallback<GetReviewsResponse> callback) {
        createMockResponse("business-reviews.json", callback, GetReviewsResponse.class);
    }

    private <T> void createMockResponse(String fileName, NetworkCallback<T> callback,
        Class<T> clazz) {
        String json = loadFileFromAssets(fileName);

        try {
            T response = new Gson().fromJson(json, clazz);
            if (response != null) {
                callback.onSuccess(response);
            } else {
                callback.onError(new Exception("Response is null"));
            }
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    private String loadFileFromAssets(String fileName) {
        String json;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        return json;
    }

}