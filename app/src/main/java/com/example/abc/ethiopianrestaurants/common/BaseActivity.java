package com.example.abc.ethiopianrestaurants.common;

import android.os.Bundle;

import com.example.abc.ethiopianrestaurants.BuildConfig;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        bindViews();

        if (BuildConfig.DEV_MODE && Timber.forest().isEmpty()) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void bindViews();
}
