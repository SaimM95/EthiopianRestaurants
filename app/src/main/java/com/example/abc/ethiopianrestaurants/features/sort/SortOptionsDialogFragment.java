package com.example.abc.ethiopianrestaurants.features.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.abc.ethiopianrestaurants.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SortOptionsDialogFragment extends BottomSheetDialogFragment {

    @Nullable private OnSortOptionSelectedListener listener;

    public static SortOptionsDialogFragment newInstance(OnSortOptionSelectedListener listener) {
        SortOptionsDialogFragment fragment = new SortOptionsDialogFragment();
        fragment.setListener(listener);
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

        RadioGroup radioGroup = view.findViewById(R.id.sort_options_radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.sort_options_name:
                    invokeListener(SortOption.NAME);
                    break;
                case R.id.sort_options_rating:
                    invokeListener(SortOption.RATING);
                    break;
                case R.id.sort_options_reviews_count:
                    invokeListener(SortOption.REVIEWS);
                    break;
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public void setListener(@Nullable OnSortOptionSelectedListener listener) {
        this.listener = listener;
    }

    private void invokeListener(SortOption sortOption) {
        if (listener != null) {
            listener.onSortOptionSelected(sortOption);
        }
        dismiss();
    }
}
