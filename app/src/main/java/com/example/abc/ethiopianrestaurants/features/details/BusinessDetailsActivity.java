package com.example.abc.ethiopianrestaurants.features.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.AndroidUtils;
import com.example.abc.ethiopianrestaurants.common.BaseActivity;
import com.example.abc.ethiopianrestaurants.common.SimpleVerticalDividerItemDecoration;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkClientProvider;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

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
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvReviewsHeading;
    private RecyclerView rvReviews;
    private BusinessDetailsPresenter presenter;
    private ReviewsListAdapter reviewsListAdapter;

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
        tvPhone = findViewById(R.id.business_details_tv_phone_number);
        tvAddress = findViewById(R.id.business_details_tv_address);
        tvReviewsHeading = findViewById(R.id.business_details_tv_reviews_heading);
        rvReviews = findViewById(R.id.business_details_rv_reviews);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        initListeners();

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
//        ratingBar.setVisibility(View.VISIBLE);
        ratingBar.setRating((float)rating);
    }

    @Override
    public void showReviewsCount(int reviewsCount) {
//        tvReviews.setVisibility(View.VISIBLE);
        tvReviews.setText(getString(R.string.reviews_count_text, reviewsCount));
    }

    @Override
    public void showPhoneNumber(String phoneNumber) {
//        tvPhone.setVisibility(View.VISIBLE);
        tvPhone.setText(phoneNumber);
    }

    @Override
    public void showAddress(String address) {
//        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText(address);
    }

    @Override
    public void showReviews(String businessId) {
        // TODO: open dialog fragment
    }

    @Override
    public void setContentVisible(boolean visible) {
        ratingBar.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvReviews.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvPhone.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvAddress.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvReviewsHeading.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
//        if (loading) {
//            hideContent();
//        }
    }

    @Override
    public void showError() {

    }

    private void hideContent() {
        ratingBar.setVisibility(View.GONE);
        tvReviews.setVisibility(View.GONE);
        tvPhone.setVisibility(View.GONE);
        tvAddress.setVisibility(View.GONE);
        tvReviewsHeading.setVisibility(View.GONE);
    }

    private void initListeners() {
        // TODO: check if this is bad for performance
        //        appBarLayout.addOnOffsetChangedListener((appBarLayout, offset) -> {
        //            if (Math.abs(offset) == appBarLayout.getTotalScrollRange()) {
        //                toolbar.setTitle(title);
        //            } else {
        //                toolbar.setTitle(EMPTY_TITLE);
        //            }
        //        });

        tvPhone.setOnClickListener(v ->
            AndroidUtils.openPhoneIntent(this, tvPhone.getText().toString()));

        tvAddress.setOnClickListener(v ->
            AndroidUtils.openMapsIntent(this, tvAddress.getText().toString()));

        tvReviewsHeading.setOnClickListener(v -> presenter.onReviewsClicked());
    }

    private void initRecyclerView() {
        reviewsListAdapter = new ReviewsListAdapter();
        rvReviews.setAdapter(reviewsListAdapter);

        SimpleVerticalDividerItemDecoration itemDecoration =
            new SimpleVerticalDividerItemDecoration.Builder(this)
                .setFirstItemHasTopDivider(false)
                .setLastItemHasDivider(false)
                .setLeftMargin(R.dimen.review_item_divider_side_padding)
                .setRightMargin(R.dimen.review_item_divider_side_padding)
                .setColorRes(R.color.review_items_list_divider_color)
                .setTopPadding(R.dimen.review_item_vertical_padding)
                .setBottomPadding(R.dimen.review_item_vertical_padding)
                .addPaddingAboveFirstItem(false)
                .build();
        rvReviews.addItemDecoration(itemDecoration);
    }
}
