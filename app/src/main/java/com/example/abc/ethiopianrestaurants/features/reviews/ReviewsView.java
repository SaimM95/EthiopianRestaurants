package com.example.abc.ethiopianrestaurants.features.reviews;

import com.example.abc.ethiopianrestaurants.common.BaseView;
import com.example.abc.ethiopianrestaurants.model.Review;

import java.util.List;

public interface ReviewsView extends BaseView {

    void showReviews(List<Review> reviews);
}
