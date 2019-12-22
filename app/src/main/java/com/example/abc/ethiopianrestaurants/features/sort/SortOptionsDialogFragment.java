package com.example.abc.ethiopianrestaurants.features.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.abc.ethiopianrestaurants.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SortOptionsDialogFragment extends BottomSheetDialogFragment {

    private RadioGroup radioGroup;
    @Nullable private OnSortOptionSelectedListener listener;
    @Nullable private SortOption defaultOption;

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = (group, checkedId) -> {
        switch (checkedId) {
            case R.id.sort_option_name:
                invokeListener(SortOption.NAME);
                break;
            case R.id.sort_option_rating:
                invokeListener(SortOption.RATING);
                break;
            case R.id.sort_option_reviews_count:
                invokeListener(SortOption.REVIEWS);
                break;
        }
    };

    public static SortOptionsDialogFragment newInstance(OnSortOptionSelectedListener listener,
        SortOption defaultOption) {
        SortOptionsDialogFragment fragment = new SortOptionsDialogFragment();
        fragment.setListener(listener);
        fragment.setDefaultOption(defaultOption);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_sort_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = view.findViewById(R.id.sort_options_radio_group);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

        if (defaultOption != null) {
            switch (defaultOption) {
                case NAME:
                    setDefaultSelection(view, R.id.sort_option_name);
                    break;
                case RATING:
                    setDefaultSelection(view, R.id.sort_option_rating);
                    break;
                case REVIEWS:
                    setDefaultSelection(view, R.id.sort_option_reviews_count);
                    break;
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void setListener(@Nullable OnSortOptionSelectedListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("WeakerAccess")
    public void setDefaultOption(@Nullable SortOption defaultOption) {
        this.defaultOption = defaultOption;
    }

    private void setDefaultSelection(View view, int id) {
        radioGroup.setOnCheckedChangeListener(null);
        ((RadioButton)view.findViewById(id)).setChecked(true);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private void invokeListener(SortOption sortOption) {
        if (listener != null) {
            listener.onSortOptionSelected(sortOption);
        }
        dismiss();
    }
}
