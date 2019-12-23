package com.example.abc.ethiopianrestaurants.features.search;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetBusinessesResponse;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SearchPresenter extends BasePresenter<SearchView> {

    private final BusinessNetworkClient businessNetworkClient;
    @Nullable private Disposable disposable;

    SearchPresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady(String searchQuery) {
        showLoading(true);
        disposable = businessNetworkClient.getBusinesses(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> showLoading(false))
            .subscribe(this::onGetBusinessesSuccessful, throwable -> {
                Timber.e(throwable);
                showError();
            });
    }

    void onViewDestroyed() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void onGetBusinessesSuccessful(GetBusinessesResponse response) {
        if (response == null) {
            showError();
        } else {
            List<Business> businesses = response.businesses;
            Map<String, List<String>> categoryBusinesses = new HashMap<>();

            for (Business business : businesses) {
                String businessName = business.name;
                for (Business.Category category : business.categories) {
                    String categoryName = category.title;
                    if (categoryBusinesses.containsKey(categoryName)) {
                        List<String> businessNames = categoryBusinesses.get(categoryName);
                        businessNames.add(businessName);
                    } else {
                        List<String> businessNames = new ArrayList<>();
                        businessNames.add(businessName);
                        categoryBusinesses.put(categoryName, businessNames);
                    }
                }
            }

            List<String> categorizedBusinesses = new ArrayList<>();
            Map<String, Integer> categoryCountMap = new HashMap<>();

            for (String categoryName : categoryBusinesses.keySet()) {
                categorizedBusinesses.add(categoryName);
                List<String> businessNames = categoryBusinesses.get(categoryName);
                categorizedBusinesses.addAll(businessNames);
                categoryCountMap.put(categoryName, businessNames.size());
            }

            executeViewOperation(() -> view.showSearchResults(categoryBusinesses.keySet(),
                categorizedBusinesses, categoryCountMap));
        }
    }
}
