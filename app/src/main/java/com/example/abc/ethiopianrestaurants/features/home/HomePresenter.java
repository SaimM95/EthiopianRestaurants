package com.example.abc.ethiopianrestaurants.features.home;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.Utils;
import com.example.abc.ethiopianrestaurants.features.sort.SortOption;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class HomePresenter extends BasePresenter<HomeView> {

    private static final String SEARCH_TERM = "Ehiopian";
    private static final int LIMIT = 10;

    // Sort comparators
    private final Comparator<Business> SORT_BY_NAME = (b1, b2) -> b1.name.compareTo(b2.name);
    private final Comparator<Business> SORT_BY_RATING =
        (b1, b2) -> Double.compare(b1.rating, b2.rating);
    private final Comparator<Business> SORT_BY_REVIEWS =
        (b1, b2) -> Integer.compare(b1.reviewCount, b2.reviewCount);

    private final BusinessNetworkClient businessNetworkClient;
    private List<Business> businesses = new ArrayList<>();
    @Nullable private Disposable disposable;
    @Nullable private SortOption selectedSortOption;

    HomePresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady() {
        showLoading(true);
        disposable = businessNetworkClient.getBusinesses(SEARCH_TERM, LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> showLoading(false))
            .subscribe(response -> {
                if (Utils.isSafe(response.businesses)) {
                    this.businesses = response.businesses;
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

    void onSortButtonClicked() {
        executeViewOperation(() -> view.showSortOptions(selectedSortOption));
    }

    void onSortOptionSelected(SortOption sortOption) {
        if (selectedSortOption == sortOption) {
            return;
        }

        selectedSortOption = sortOption;
        switch (sortOption) {
            case NAME:
                Collections.sort(businesses, SORT_BY_NAME);
                break;
            case RATING:
                Collections.sort(businesses, SORT_BY_RATING);
                break;
            case REVIEWS:
                Collections.sort(businesses, SORT_BY_REVIEWS);
                break;
        }
        executeViewOperation(() -> view.populateBusinesses(businesses));
    }

    void onBusinessItemClicked(Business business) {
        executeViewOperation(() -> view.navigateToDetails(business.id));
    }
}
