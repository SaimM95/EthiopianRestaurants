package com.example.abc.ethiopianrestaurants.features.home;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.Utils;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import java.util.List;

class HomePresenter extends BasePresenter<HomeView> {

    private static final String SEARCH_TERM = "Ehiopian";
    // Long/Lat for Toronto
    private static final double LATITUDE = 43.651070;
    private static final double LONGITUDE = -79.347015;
    private static final int LIMIT = 10;

    private final BusinessNetworkClient businessNetworkClient;
    @Nullable private Disposable disposable;

    HomePresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady() {
        showLoading(true);
        disposable = businessNetworkClient.getBusinesses(SEARCH_TERM, LATITUDE, LONGITUDE, LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> showLoading(false))
            .subscribe(response -> {
                final List<Business> businesses = response.businesses;
                if (Utils.isSafe(businesses)) {
                    executeViewOperation(() -> view.populateBusinesses(businesses));
                } else {
                    showError();
                }
            }, throwable -> {
                Timber.e(throwable);
                showError();
            });
    }

    void onViewDestroyed() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    void onBusinessItemClicked(Business business) {
        executeViewOperation(() -> view.navigateToDetails(business.id));
    }
}
