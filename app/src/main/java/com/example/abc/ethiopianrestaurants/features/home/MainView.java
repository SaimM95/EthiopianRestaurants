package com.example.abc.ethiopianrestaurants.features.home;

import com.example.abc.ethiopianrestaurants.common.BaseView;
import com.example.abc.ethiopianrestaurants.model.Business;

import java.util.List;

public interface MainView extends BaseView {

    void populateBusinesses(List<Business> businesses);

    void navigateToDetails(String businessId);
}
