package com.example.abc.ethiopianrestaurants.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

public class AndroidUtils {

    private AndroidUtils() {
        // empty
    }

    public static void openPhoneIntent(@NonNull Context context, @NonNull String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(phoneIntent);
    }

    public static void openMapsIntent(@NonNull Context context, @NonNull String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }
}
