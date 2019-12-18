package com.example.abc.ethiopianrestaurants.network;

import android.content.Context;

public class NetworkClientProvider {

    private static final boolean USE_MOCK = true;

    private NetworkClientProvider() {
        // empty
    }

    public static BusinessNetworkClient getBusinessNetworkClient(Context context) {
        if (USE_MOCK) {
            return new MockNetworkClient(context);
        }
        // TODO: return real client
        return null;
    }
}
