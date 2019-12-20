package com.example.abc.ethiopianrestaurants.features.home;

import com.example.abc.ethiopianrestaurants.model.Business;

import java.util.List;

public interface MainView {

    void showLoading(boolean loading);

    void populateBusinesses(List<Business> businesses);

    void showError();
}
