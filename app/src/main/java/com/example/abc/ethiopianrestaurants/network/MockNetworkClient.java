package com.example.abc.ethiopianrestaurants.network;

import android.content.Context;
import android.os.Handler;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;
import com.google.gson.Gson;

import timber.log.Timber;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MockNetworkClient implements BusinessNetworkClient {

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

        // add delay to simulate loading
        Runnable runnable = () -> {
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
        };
        new Handler().postDelayed(runnable, 2000);
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
            Timber.e(e);
            return null;
        }
        return json;
    }

}
