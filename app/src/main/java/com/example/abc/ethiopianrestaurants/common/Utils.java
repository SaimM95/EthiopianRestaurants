package com.example.abc.ethiopianrestaurants.common;

import java.util.List;

public class Utils {

    private Utils() {
        // no-op
    }

    public static boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static boolean isSafe(String text) {
        return !isNullOrEmpty(text);
    }

    public static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isSafe(List list) {
        return !isNullOrEmpty(list);
    }

}
