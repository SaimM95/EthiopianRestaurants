package com.example.abc.ethiopianrestaurants.features.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.Review;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;

public class BusinessDetailsAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_DETAILS = 1;
    private static final int VIEW_TYPE_REVIEWS = 2;

    @Nullable private Business business;
    private List<Review> reviews = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder: %s", viewType == VIEW_TYPE_DETAILS ? "Details" : "Reviews");
        if (viewType == VIEW_TYPE_DETAILS) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_business_details,
                    parent, false);
            return new BusinessDetailsViewHolder(view, business);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review_item,
                parent, false);
            return new ReviewViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Timber.d("onBindViewHolder: %s",
            getItemViewType(position) == VIEW_TYPE_DETAILS ? "Details" : "Reviews");
        if (holder instanceof BusinessDetailsViewHolder) {
            ((BusinessDetailsViewHolder)holder).bindView(business);
        } else if (holder instanceof ReviewViewHolder) {
            Review review = getReviewFromAdapterPosition(position);
            ((ReviewViewHolder)holder).bindView(review);
        }
    }

    @Override
    public int getItemCount() {
        // add 1 to accommodate for business details view
        return business != null ? 1 + reviews.size() : reviews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_DETAILS : VIEW_TYPE_REVIEWS;
    }

    private Review getReviewFromAdapterPosition(int adapterPosition) {
        // subtract 1 to accommodate for business details view
        return business == null ? reviews.get(adapterPosition) : reviews.get(adapterPosition - 1);
    }

    void updateBusinessDetails(Business business) {
        this.business = business;
        notifyItemInserted(0);
    }

    void updateReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyItemRangeInserted(1, reviews.size());
    }
}
