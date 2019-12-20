package com.example.abc.ethiopianrestaurants.features.details;

import com.example.abc.ethiopianrestaurants.common.BaseView;

public interface BusinessDetailsView extends BaseView {

    void setTitle(String businessName);

    void showImage(String imageUrl);

    void showRating(double rating);

    void showReviewsCount(int reviewsCount);
}
