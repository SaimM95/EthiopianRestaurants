package com.example.abc.ethiopianrestaurants.features.details;

import com.example.abc.ethiopianrestaurants.common.BaseView;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.Review;

import java.util.List;

public interface BusinessDetailsView extends BaseView {

    void setTitle(String businessName);

    void showImage(String imageUrl);

    void showBusinessDetails(Business business);

    void showReviews(List<Review> reviews);
}
