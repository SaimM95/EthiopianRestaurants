package com.example.abc.ethiopianrestaurants.features.reviews;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.Utils;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;
import com.example.abc.ethiopianrestaurants.model.Review;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkCallback;

import timber.log.Timber;

import java.util.List;

public class ReviewsPresenter extends BasePresenter<ReviewsView> {

    private final BusinessNetworkClient businessNetworkClient;

    public ReviewsPresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady(String businessId) {
        if (Utils.isNullOrEmpty(businessId)) {
            showError();
            return;
        }

        showLoading(true);
        businessNetworkClient.getBusinessReviews(businessId,
            new NetworkCallback<GetReviewsResponse>() {
                @Override
                public void onSuccess(GetReviewsResponse response) {
                    List<Review> reviews = response.reviews;
                    executeViewOperation(() -> view.showReviews(reviews));
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
