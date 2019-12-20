package com.example.abc.ethiopianrestaurants;

import android.os.Bundle;
import android.view.View;

import com.example.abc.ethiopianrestaurants.model.Business;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkClientProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private RecyclerView rvBusinesses;
    private View errorMessage;
    private View progressBar;
    private BusinessListAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initRecyclerView();

        BusinessNetworkClient networkClient =
            NetworkClientProvider.getBusinessNetworkClient(this);
        presenter = new MainPresenter(networkClient);
        presenter.bindView(this);
        presenter.onViewReady();
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
            adapter.submitList(businesses);
        }
    }

    @Override
    public void showError() {
        rvBusinesses.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void bindViews() {
        rvBusinesses = findViewById(R.id.home_page_rv_businesses);
        errorMessage = findViewById(R.id.home_page_error_message);
        progressBar = findViewById(R.id.home_page_progress_bar);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvBusinesses.setLayoutManager(gridLayoutManager);

        adapter = new BusinessListAdapter();
        rvBusinesses.setAdapter(adapter);
    }
}
