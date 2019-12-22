package com.example.abc.ethiopianrestaurants.features.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.BaseActivity;
import com.example.abc.ethiopianrestaurants.common.SimpleVerticalDividerItemDecoration;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.model.Review;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkClientProvider;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BusinessDetailsActivity extends BaseActivity implements BusinessDetailsView {

    private static final String PARAM_BUSINESS_ID = "business_id";
    private static final String EMPTY_TITLE = " ";

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private ProgressBar progressBar;
    private ImageView ivDetailsImage;
    private RecyclerView rvBusinessDetails;
    private BusinessDetailsPresenter presenter;
    private BusinessDetailsAdapter businessDetailsAdapter;
    private View errorMessage;

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
        rvBusinessDetails = findViewById(R.id.rv_business_details);
        errorMessage = findViewById(R.id.business_details_error_message);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        initViews();

        BusinessNetworkClient networkClient =
            NetworkClientProvider.getBusinessNetworkClient(this);
        presenter = new BusinessDetailsPresenter(networkClient);
        presenter.bindView(this);

        String businessId = getIntent() == null ? null
            : getIntent().getStringExtra(PARAM_BUSINESS_ID);
        presenter.onViewReady(businessId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.bindView(this);
        presenter.updateState();
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
    public void showBusinessDetails(Business business) {
        businessDetailsAdapter.updateBusinessDetails(business);
    }

    @Override
    public void showReviews(List<Review> reviews) {
        businessDetailsAdapter.updateReviews(reviews);
        new Handler().post(() -> rvBusinessDetails.scrollToPosition(0));
    }

    @Override
    public void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError() {
        // TODO: some ting wong
        rvBusinessDetails.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        // TODO: check if this is bad for performance
        //        appBarLayout.addOnOffsetChangedListener((appBarLayout, offset) -> {
        //            if (Math.abs(offset) == appBarLayout.getTotalScrollRange()) {
        //                toolbar.setTitle(title);
        //            } else {
        //                toolbar.setTitle(EMPTY_TITLE);
        //            }
        //        });

        // init recycler view
        businessDetailsAdapter = new BusinessDetailsAdapter();
        rvBusinessDetails.setAdapter(businessDetailsAdapter);

        SimpleVerticalDividerItemDecoration itemDecoration =
            new SimpleVerticalDividerItemDecoration.Builder(this)
                .setColorRes(R.color.review_items_list_divider_color)
                .setFirstItemHasTopDivider(false)
                .setLastItemHasDivider(false)
                .setLeftMargin(R.dimen.review_item_divider_side_padding)
                .setRightMargin(R.dimen.review_item_divider_side_padding)
                .setTopPadding(R.dimen.review_item_vertical_padding)
                .setBottomPadding(R.dimen.review_item_vertical_padding)
                .addPaddingAboveFirstItem(true)
                .addPaddingBelowLastItem(true)
                .setFirstItemPosition(1)
                .build();
        rvBusinessDetails.addItemDecoration(itemDecoration);
    }
}
