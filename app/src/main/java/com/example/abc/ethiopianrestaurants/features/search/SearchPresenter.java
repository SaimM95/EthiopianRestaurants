package com.example.abc.ethiopianrestaurants.features.search;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.ListItem;
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

    void onBusinessClicked(Business business) {
        executeViewOperation(() -> view.navigateToBusinessDetails(business.id));
    }

    @SuppressWarnings("ConstantConditions")
    private void onGetBusinessesSuccessful(GetBusinessesResponse response) {
        if (response == null) {
            showError();
        } else {
            // Create map of category to businesses
            Map<String, List<Business>> categoryBusinesses = new HashMap<>();

            for (Business business : response.businesses) {
                for (Business.Category category : business.categories) {
                    String categoryName = category.title;
                    if (categoryBusinesses.containsKey(categoryName)) {
                        List<Business> businesses = categoryBusinesses.get(categoryName);
                        businesses.add(business);
                    } else {
                        List<Business> businesses = new ArrayList<>();
                        businesses.add(business);
                        categoryBusinesses.put(categoryName, businesses);
                    }
                }
            }

            List<ListItem> listItems = new ArrayList<>();
            Map<String, Integer> categoryCountMap = new HashMap<>();

            for (String categoryName : categoryBusinesses.keySet()) {
                listItems.add(new ListItem<>(ListItem.ViewType.CATEGORY, categoryName));
                List<Business> businesses = categoryBusinesses.get(categoryName);
                for (Business business : businesses) {
                    listItems.add(new ListItem<>(ListItem.ViewType.BUSINESS, business));
                }
                categoryCountMap.put(categoryName, businesses.size());
            }

            executeViewOperation(() -> view.showSearchResults(listItems, categoryCountMap));
        }
    }
}
