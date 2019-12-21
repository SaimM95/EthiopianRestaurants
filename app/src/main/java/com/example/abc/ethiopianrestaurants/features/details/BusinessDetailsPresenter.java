package com.example.abc.ethiopianrestaurants.features.details;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.Utils;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;
import com.example.abc.ethiopianrestaurants.model.Review;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkCallback;

import androidx.annotation.Nullable;
import timber.log.Timber;

import java.util.List;

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

        // TODO: chain these two network calls
        businessNetworkClient.getBusinessDetails(businessId, new NetworkCallback<Business>() {
            @Override
            public void onSuccess(Business business) {
                executeViewOperation(() -> {
                    view.setTitle(business.name);
                    view.showImage(business.imageUrl);
                    view.showBusinessDetails(business);
                });
                showLoading(false);
            }

            @Override
            public void onError(Throwable throwable) {
                Timber.e(throwable);
                showError();
            }
        });

        businessNetworkClient.getBusinessReviews(businessId,
            new NetworkCallback<GetReviewsResponse>() {
                @Override
                public void onSuccess(GetReviewsResponse response) {
                    List<Review> reviews = response.reviews;
                    executeViewOperation(() -> view.showReviews(reviews));
                }

                @Override
                public void onError(Throwable throwable) {
                    Timber.e(throwable);
                }
            });
    }
}
