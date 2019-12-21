package com.example.abc.ethiopianrestaurants.features.reviews;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.SimpleVerticalDividerItemDecoration;
import com.example.abc.ethiopianrestaurants.features.details.ReviewsListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsDialogFragment extends DialogFragment {

    private RecyclerView rvReviews;
    private ReviewsListAdapter reviewsListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        rvReviews = new RecyclerView(requireContext());
        return rvReviews;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reviewsListAdapter = new ReviewsListAdapter();
        rvReviews.setAdapter(reviewsListAdapter);

        SimpleVerticalDividerItemDecoration itemDecoration =
            new SimpleVerticalDividerItemDecoration.Builder(requireContext())
                .setFirstItemHasTopDivider(false)
                .setLastItemHasDivider(false)
                .setLeftMargin(R.dimen.review_item_divider_side_padding)
                .setRightMargin(R.dimen.review_item_divider_side_padding)
                .setColorRes(R.color.review_items_list_divider_color)
                .setTopPadding(R.dimen.review_item_vertical_padding)
                .setBottomPadding(R.dimen.review_item_vertical_padding)
                .addPaddingAboveFirstItem(false)
                .build();
        rvReviews.addItemDecoration(itemDecoration);
    }
}
