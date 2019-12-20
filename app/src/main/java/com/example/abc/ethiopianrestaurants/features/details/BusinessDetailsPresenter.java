package com.example.abc.ethiopianrestaurants.features.details;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.Utils;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkCallback;

import androidx.annotation.Nullable;
import timber.log.Timber;

class BusinessDetailsPresenter extends BasePresenter<BusinessDetailsView> {

    private BusinessNetworkClient businessNetworkClient;

    BusinessDetailsPresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady(@Nullable String businessId) {
        if (Utils.isNullOrEmpty(businessId)) {
            showError();
            return;
        }

        showLoading(true);
        businessNetworkClient.getBusinessDetails(businessId, new NetworkCallback<Business>() {
            @Override
            public void onSuccess(Business business) {
                executeViewOperation(() -> {
                    view.setTitle(business.name);
                    view.showImage(business.imageUrl);
                    view.showRating(business.rating);
                    view.showReviewsCount(business.reviewCount);
                });
                showLoading(false);
            }

            @Override
            public void onError(Throwable throwable) {
                Timber.e(throwable);
                showError();
            }
        });
    }
}
