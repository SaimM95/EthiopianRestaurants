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
    @Nullable String businessId;

    BusinessDetailsPresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady(@Nullable String businessId) {
        if (Utils.isNullOrEmpty(businessId)) {
            showError();
            return;
        }

        this.businessId = businessId;
        showLoading(true);
        executeViewOperation(() -> view.setContentVisible(false));

        businessNetworkClient.getBusinessDetails(businessId, new NetworkCallback<Business>() {
            @Override
            public void onSuccess(Business business) {
                executeViewOperation(() -> {
                    view.setContentVisible(true);
                    view.setTitle(business.name);
                    view.showImage(business.imageUrl);
                    view.showRating(business.rating);
                    view.showReviewsCount(business.reviewCount);
                    view.showPhoneNumber(business.phone);
                    if (business.location != null) {
                        view.showAddress(business.location.address);
                    }
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

    void onReviewsClicked() {
        if (Utils.isSafe(businessId)) {
            executeViewOperation(() -> view.showReviews(businessId));
        }
    }
}
