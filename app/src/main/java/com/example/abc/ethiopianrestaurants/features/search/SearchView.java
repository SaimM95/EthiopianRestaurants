package com.example.abc.ethiopianrestaurants.features.search;

import com.example.abc.ethiopianrestaurants.common.BaseView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchView extends BaseView {

    void showSearchResults(Set<String> categorySet, List<String> categorizedBusinesses,
        Map<String, Integer> categoryCountMap);
}
