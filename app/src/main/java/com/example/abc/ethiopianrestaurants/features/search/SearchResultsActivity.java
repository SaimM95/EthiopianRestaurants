package com.example.abc.ethiopianrestaurants.features.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.BaseActivity;
import com.example.abc.ethiopianrestaurants.common.SimpleVerticalDividerItemDecoration;
import com.example.abc.ethiopianrestaurants.network.BusinessNetworkClient;
import com.example.abc.ethiopianrestaurants.network.NetworkClientProvider;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchResultsActivity extends BaseActivity implements SearchView {

    private View progressBar;
    private View errorMessage;
    private RecyclerView rvSearchResults;
    private SearchResultsAdapter adapter;
    private SearchPresenter presenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void bindViews() {
        progressBar = findViewById(R.id.search_results_progress_bar);
        errorMessage = findViewById(R.id.search_results_error_message);
        rvSearchResults = findViewById(R.id.rv_search_results);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();

        BusinessNetworkClient networkClient =
            NetworkClientProvider.getBusinessNetworkClient(this);
        presenter = new SearchPresenter(networkClient);
        presenter.bindView(this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
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

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            presenter.onViewReady(query);
        }
    }

    @Override
    public void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError() {
        rvSearchResults.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchResults(Set<String> categorySet, List<String> categorizedBusinesses,
        Map<String, Integer> categoryCountMap) {
        adapter.updateItems(categorySet, categorizedBusinesses, categoryCountMap);
    }

    private void initViews() {
        adapter = new SearchResultsAdapter();
        rvSearchResults.setAdapter(adapter);

        SimpleVerticalDividerItemDecoration itemDecoration =
            new SimpleVerticalDividerItemDecoration.Builder(this)
                .setColorRes(R.color.list_item_divider_color)
                .setLastItemHasDivider(false)
                .build();
        rvSearchResults.addItemDecoration(itemDecoration);
    }
}
