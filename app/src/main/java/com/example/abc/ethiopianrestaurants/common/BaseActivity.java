package com.example.abc.ethiopianrestaurants.common;

import android.os.Bundle;

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

        // TODO: should be used in dev mode only
        if (Timber.forest().isEmpty()) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void bindViews();
}
