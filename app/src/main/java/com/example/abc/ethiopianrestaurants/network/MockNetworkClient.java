package com.example.abc.ethiopianrestaurants.network;

import android.content.Context;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;
import com.google.gson.Gson;

import io.reactivex.Single;
import timber.log.Timber;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class MockNetworkClient implements BusinessNetworkClient {

    private final Context context;

    MockNetworkClient(Context context) {
        this.context = context;
    }

    @Override
    public Single<GetBusinessesResponse> getBusinesses(String searchTerm) {
        return createMockResponse("businesses.json", GetBusinessesResponse.class);
    }

    @Override
    public Single<GetBusinessesResponse> getBusinesses(String searchTerm, int limit) {
        return createMockResponse("businesses.json", GetBusinessesResponse.class);
    }

    @Override
    public Single<Business> getBusinessDetails(String businessId) {
        return createMockResponse("business-details.json", Business.class);
    }

    @Override
    public Single<GetReviewsResponse> getBusinessReviews(String businessId) {
        return createMockResponse("business-reviews.json", GetReviewsResponse.class);
    }

    private <T> Single<T> createMockResponse(String fileName, Class<T> clazz) {
        String json = loadFileFromAssets(fileName);
        Single<T> single = Single.create(e -> {
            try {
                T response = new Gson().fromJson(json, clazz);
                if (response != null) {
                    e.onSuccess(response);
                } else {
                    e.onError(new Exception("Response is null"));
                }
            } catch (Exception exception) {
                e.onError(exception);
            }
        });

        single = single.delay(1, TimeUnit.SECONDS);
        return single;
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
