package com.example.abc.ethiopianrestaurants.features.home;

import com.example.abc.ethiopianrestaurants.common.BaseView;
import com.example.abc.ethiopianrestaurants.features.sort.SortOption;
import com.example.abc.ethiopianrestaurants.model.Business;

import androidx.annotation.Nullable;

import java.util.List;

public interface HomeView extends BaseView {

    void populateBusinesses(List<Business> businesses);

    void showSortOptions(@Nullable SortOption selectedSortOption);

    void navigateToDetails(String businessId);
}
