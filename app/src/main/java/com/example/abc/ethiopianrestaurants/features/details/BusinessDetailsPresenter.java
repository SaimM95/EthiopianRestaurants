package com.example.abc.ethiopianrestaurants.features.details;

import com.example.abc.ethiopianrestaurants.common.BasePresenter;
import com.example.abc.ethiopianrestaurants.common.Utils;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.GetReviewsResponse;
import com.example.abc.ethiopianrestaurants.model.Review;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;

import androidx.annotation.Nullable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import java.util.List;

class BusinessDetailsPresenter extends BasePresenter<BusinessDetailsView> {

    private BusinessNetworkClient businessNetworkClient;
    @Nullable private Disposable disposable;

    BusinessDetailsPresenter(BusinessNetworkClient businessNetworkClient) {
        this.businessNetworkClient = businessNetworkClient;
    }

    void onViewReady(@Nullable String businessId) {
        if (Utils.isNullOrEmpty(businessId)) {
            showError();
            return;
        }

        Single<Business> getBusinessDetails = businessNetworkClient.getBusinessDetails(businessId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(business -> {
                executeViewOperation(() -> {
                    view.setTitle(business.name);
                    view.showImage(business.imageUrl);
                    view.showBusinessDetails(business);
                });
                return business;
            });

        Single<GetReviewsResponse> getReviews =
            businessNetworkClient.getBusinessReviews(businessId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    List<Review> reviews = response.reviews;
                    executeViewOperation(() -> view.showReviews(reviews));
                    return response;
                });

        showLoading(true);
        disposable = Single.merge(getBusinessDetails, getReviews)
            .doFinally(() -> showLoading(false))
            .subscribe(response -> {
                // no-op
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
}
