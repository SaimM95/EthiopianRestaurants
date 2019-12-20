package com.example.abc.ethiopianrestaurants.features.home;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.Utils;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkCallback;

import java.util.List;

class MainPresenter extends BasePresenter<MainView> {

    private static final String SEARCH_TERM = "Ehiopian";
    // Long/Lat for Toronto
    private static final double LONGITUDE = -79.347015;
    private static final double LATITUDE = 43.651070;
    private static final int LIMIT = 10;

    private final BusinessNetworkClient businessNetworkClient;

    MainPresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady() {
        showLoading(true);
        businessNetworkClient.getBusinesses(SEARCH_TERM, LONGITUDE, LATITUDE, LIMIT,
            new NetworkCallback<GetBusinessesResponse>() {
                @Override
                public void onSuccess(GetBusinessesResponse response) {
                    final List<Business> businesses = response.businesses;
                    if (Utils.isSafe(businesses)) {
                        executeViewOperation(() -> view.populateBusinesses(businesses));
                    } else {
                        executeViewOperation(() -> view.showError());
                    }
                    showLoading(false);
                }

                @Override
                public void onError(Throwable throwable) {
                    executeViewOperation(() -> view.showError());
                    showLoading(false);
                }
            });
    }

    private void showLoading(boolean loading) {
        executeViewOperation(() -> view.showLoading(loading));
    }
}
