package com.example.abc.ethiopianrestaurants.network;

import android.content.Context;

public class NetworkClientProvider {

    // TODO fix this boolean
    private static final boolean USE_MOCK = false;

    private NetworkClientProvider() {
        // empty
    }

    public static BusinessNetworkClient getBusinessNetworkClient(Context context) {
        if (USE_MOCK) {
            return new MockNetworkClient(context);
        }
        return new RetrofitNetworkClient();
    }
}
