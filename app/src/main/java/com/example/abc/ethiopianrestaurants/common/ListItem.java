package com.example.abc.ethiopianrestaurants.common;

public class ListItem<T> {

    public enum ViewType {
        CATEGORY, BUSINESS
    }

    public final ViewType viewType;
    public final T value;

    public ListItem(ViewType viewType, T value) {
        this.viewType = viewType;
        this.value = value;
    }
}
