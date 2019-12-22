package com.example.abc.ethiopianrestaurants.features.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.BaseActivity;
import com.example.abc.ethiopianrestaurants.features.details.BusinessDetailsActivity;
import com.example.abc.ethiopianrestaurants.features.sort.OnSortOptionSelectedListener;
import com.example.abc.ethiopianrestaurants.features.sort.SortOption;
import com.example.abc.ethiopianrestaurants.features.sort.SortOptionsDialogFragment;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkClientProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeView, OnSortOptionSelectedListener {

    private RecyclerView rvBusinesses;
    private View errorMessage;
    private View progressBar;
    private FloatingActionButton fabSort;
    private BusinessListAdapter adapter;
    private HomePresenter presenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void bindViews() {
        rvBusinesses = findViewById(R.id.home_page_rv_businesses);
        errorMessage = findViewById(R.id.home_page_error_message);
        progressBar = findViewById(R.id.home_page_progress_bar);
        fabSort = findViewById(R.id.home_page_fab_sort);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        BusinessNetworkClient networkClient =
            NetworkClientProvider.getBusinessNetworkClient(this);
        presenter = new HomePresenter(networkClient);
        presenter.bindView(this);
        presenter.onViewReady();
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
    public void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        if (loading) {
            rvBusinesses.setVisibility(View.GONE);
            errorMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void populateBusinesses(List<Business> businesses) {
        rvBusinesses.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);
        if (adapter != null) {
            adapter.submitList(new ArrayList<>(businesses));
            // scroll to top after sorting
            new Handler().postDelayed(() -> rvBusinesses.scrollToPosition(0), 200);
        }
    }

    @Override
    public void showSortOptions(@Nullable SortOption selectedSortOption) {
        DialogFragment dialogFragment = SortOptionsDialogFragment
            .newInstance(this, selectedSortOption);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
            .addToBackStack(null);
        dialogFragment.show(ft, dialogFragment.getClass().getCanonicalName());
    }

    @Override
    public void showError() {
        rvBusinesses.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToDetails(String businessId) {
        BusinessDetailsActivity.open(this, businessId);
    }

    private void initViews() {
        fabSort.setOnClickListener(v -> presenter.onSortButtonClicked());

        // init recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvBusinesses.setLayoutManager(gridLayoutManager);

        adapter = new BusinessListAdapter();
        adapter.setItemClickedListener(business -> presenter.onBusinessItemClicked(business));
        rvBusinesses.setAdapter(adapter);
    }

    @Override
    public void onSortOptionSelected(SortOption sortOption) {
        presenter.onSortOptionSelected(sortOption);
    }
}
