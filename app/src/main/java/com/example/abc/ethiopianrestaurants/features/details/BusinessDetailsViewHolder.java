package com.example.abc.ethiopianrestaurants.features.details;

import android.view.View;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.AndroidUtils;
import com.example.abc.ethiopianrestaurants.model.Business;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

class BusinessDetailsViewHolder extends RecyclerView.ViewHolder {

    private AppCompatRatingBar ratingBar;
    private TextView tvReviews;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvReviewsHeading;

    BusinessDetailsViewHolder(@NonNull View itemView, @Nullable Business business) {
        super(itemView);

        ratingBar = itemView.findViewById(R.id.business_details_rating_bar);
        tvReviews = itemView.findViewById(R.id.business_details_tv_reviews);
        tvPhone = itemView.findViewById(R.id.business_details_tv_phone_number);
        tvAddress = itemView.findViewById(R.id.business_details_tv_address);
        tvReviewsHeading = itemView.findViewById(R.id.business_details_tv_reviews_heading);

        if (business != null) {
            String phone = business.phone;
            String address = business.location.address;

            tvPhone.setOnClickListener(v ->
                AndroidUtils.openPhoneIntent(itemView.getContext(), phone));

            tvAddress.setOnClickListener(v ->
                AndroidUtils.openMapsIntent(itemView.getContext(), address));
        }
    }

    void bindView(@Nullable Business business) {
        if (business != null) {
            ratingBar.setRating((float)business.rating);
            tvReviews.setText(itemView.getContext().getString(R.string.reviews_count_text,
                business.reviewCount));
            tvPhone.setText(business.phone);
            tvAddress.setText(business.location.address);
        }
    }
}
