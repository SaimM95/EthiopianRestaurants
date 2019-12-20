package com.example.abc.ethiopianrestaurants.features.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.BaseActivity;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkClientProvider;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

public class BusinessDetailsActivity extends BaseActivity implements BusinessDetailsView {

    private static final String PARAM_BUSINESS_ID = "business_id";
    private static final String EMPTY_TITLE = " ";

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private ProgressBar progressBar;
    private ImageView ivDetailsImage;
    private AppCompatRatingBar ratingBar;
    private TextView tvReviews;
    private BusinessDetailsPresenter presenter;

    private String title = EMPTY_TITLE;

    public static void open(Activity from, String businessId) {
        Intent intent = new Intent(from, BusinessDetailsActivity.class);
        intent.putExtra(PARAM_BUSINESS_ID, businessId);
        from.startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_business_details;
    }

    @Override
    protected void bindViews() {
        toolbar = findViewById(R.id.business_details_toolbar);
        collapsingToolbarLayout = findViewById(R.id.business_details_collapsing_toolbar_layout);
        appBarLayout = findViewById(R.id.business_details_app_bar_layout);
        progressBar = findViewById(R.id.business_details_progress_bar);
        ivDetailsImage = findViewById(R.id.business_details_image);
        ratingBar = findViewById(R.id.business_details_rating_bar);
        tvReviews = findViewById(R.id.business_details_tv_reviews);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        // TODO: check if this is bad for performance
        appBarLayout.addOnOffsetChangedListener((appBarLayout, offset) -> {
            if (Math.abs(offset) == appBarLayout.getTotalScrollRange()) {
                toolbar.setTitle(title);
            } else {
                toolbar.setTitle(EMPTY_TITLE);
            }
        });

        BusinessNetworkClient networkClient =
            NetworkClientProvider.getBusinessNetworkClient(this);
        presenter = new BusinessDetailsPresenter(networkClient);
        presenter.bindView(this);

        String businessId = getIntent() == null ? null
            : getIntent().getStringExtra(PARAM_BUSINESS_ID);
        presenter.onViewReady(businessId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.bindView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbindView();
    }

    @Override
    public void setTitle(String businessName) {
        this.title = businessName;
    }

    @Override
    public void showImage(String imageUrl) {
        Picasso.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_error)
            .into(ivDetailsImage);
    }

    @Override
    public void showRating(double rating) {
        ratingBar.setVisibility(View.VISIBLE);
        ratingBar.setRating((float)rating);
    }

    @Override
    public void showReviewsCount(int reviewsCount) {
        tvReviews.setVisibility(View.VISIBLE);
        tvReviews.setText(getString(R.string.reviews_count_text, reviewsCount));
    }

    @Override
    public void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        if (loading) {
            hideContent();
        }
    }

    @Override
    public void showError() {

    }

    private void hideContent() {
        ratingBar.setVisibility(View.GONE);
        tvReviews.setVisibility(View.GONE);
    }
}
