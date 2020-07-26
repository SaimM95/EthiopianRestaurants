package com.example.abc.ethiopianrestaurants.features.search;

import com.example.abc.ethiopianrestaurants.common.BaseView;
import com.example.abc.ethiopianrestaurants.common.ListItem;

import java.util.List;
import java.util.Map;

public interface SearchView extends BaseView {

    void showSearchResults(List<ListItem> listItems, Map<String, Integer> categoryCountMap);

    void navigateToBusinessDetails(String businessId);
}
