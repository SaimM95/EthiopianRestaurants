package com.example.abc.ethiopianrestaurants;

import com.example.abc.ethiopianrestaurants.model.Business;

import java.util.List;

public interface MainView {

    void showLoading(boolean loading);

    void populateBusinesses(List<Business> businesses);

    void showError();
}
