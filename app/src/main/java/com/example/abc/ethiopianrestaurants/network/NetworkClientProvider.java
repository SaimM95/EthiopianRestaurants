package com.example.abc.ethiopianrestaurants.network;

import android.content.Context;

import com.example.abc.ethiopianrestaurants.BuildConfig;

public class NetworkClientProvider {

    private static final boolean USE_MOCK = false;

    private NetworkClientProvider() {
        // empty
    }

    public static BusinessNetworkClient getBusinessNetworkClient(Context context) {
        if (BuildConfig.DEV_MODE && USE_MOCK) {
            return new MockNetworkClient(context);
        }
        return new RetrofitNetworkClient();
    }
}
