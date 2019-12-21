package com.example.abc.ethiopianrestaurants.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public final class SimpleVerticalDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_HEIGHT_RES = 1; // in dp
    private static final int DEFAULT_COLOR_RES = Color.GRAY;
    private int height;
    private boolean lastItemHasDivider;
    private boolean firstItemHasTopDivider = false;
    private int firstItemPosition = 0;
    private int leftMargin = 0;
    private int rightMargin = 0;
    private int topPadding = 0;
    private int bottomPadding = 0;
    private boolean paddingAboveFirstItem = false;
    private boolean paddingBelowLastItem = false;
    private GradientDrawable divider = new GradientDrawable();

    public SimpleVerticalDividerItemDecoration(Context context) {
        this(context, DEFAULT_HEIGHT_RES, DEFAULT_COLOR_RES, true);
    }

    public SimpleVerticalDividerItemDecoration(Context context, boolean lastItemHasDivider) {
        this(context, DEFAULT_HEIGHT_RES, DEFAULT_COLOR_RES, lastItemHasDivider);
    }

    public SimpleVerticalDividerItemDecoration(Context context, int colorRes,
        boolean lastItemHasDivider) {
        this(context, DEFAULT_HEIGHT_RES, colorRes, lastItemHasDivider);
    }

    private SimpleVerticalDividerItemDecoration(Context context, int heightRes, int color,
        boolean lastItemHasDivider) {
        this.height = (int)dpToPx(context, heightRes);
        this.lastItemHasDivider = lastItemHasDivider;
        divider.setColor(color);
    }

    private float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
        @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            Timber.e("Adapter missing for the RecyclerView");
            return;
        }

        int childPosition = parent.getChildAdapterPosition(view);
        int lastItemPosition = parent.getAdapter().getItemCount() - 1;
        boolean isFirstItem = childPosition == firstItemPosition;
        boolean isLastItem = childPosition == lastItemPosition;
        boolean isMiddleItem = childPosition > firstItemPosition
            && childPosition < lastItemPosition;

        if (isFirstItem) {
            outRect.bottom = height + bottomPadding;

            if (firstItemHasTopDivider) {
                outRect.top = height;
            }
            if (paddingAboveFirstItem) {
                outRect.top += topPadding;
            }
        } else if (isLastItem) {
            outRect.top = height + topPadding;

            if (lastItemHasDivider) {
                outRect.bottom = height;
            }
            if (paddingBelowLastItem) {
                outRect.bottom += bottomPadding;
            }
        } else if (isMiddleItem) {
            outRect.top = height + topPadding;
            outRect.bottom = height + bottomPadding;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state) {
        int left = leftMargin + parent.getLeft() + parent.getPaddingStart();
        int right = parent.getRight() - parent.getPaddingEnd() - rightMargin;

        int childCount = parent.getChildCount();
        int top, bottom;

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();

            int lastItemPosition = childCount - 1;
            boolean isFirstItem = i == firstItemPosition;
            boolean isLastItem = i == lastItemPosition;
            boolean isMiddleItem = i > firstItemPosition && i < lastItemPosition;

            if (isFirstItem) {
                if (firstItemHasTopDivider) {
                    drawTopDivider(child, params, left, right, canvas);
                }
                drawBottomDivider(child, params, left, right, canvas);
            } else if (isLastItem && lastItemHasDivider) {
                drawBottomDivider(child, params, left, right, canvas);
            } else if (isMiddleItem) {
                drawBottomDivider(child, params, left, right, canvas);
            }
        }
    }

    private void drawTopDivider(View child, RecyclerView.LayoutParams params, int left, int right,
        Canvas canvas) {
        int top = child.getTop() + params.topMargin + topPadding;
        int bottom = top + height;

        divider.setBounds(left, top, right, bottom);
        divider.draw(canvas);
    }

    private void drawBottomDivider(View child, RecyclerView.LayoutParams params, int left,
        int right, Canvas canvas) {
        int top = child.getBottom() + params.bottomMargin + bottomPadding;
        int bottom = top + height;

        divider.setBounds(left, top, right, bottom);
        divider.draw(canvas);
    }

    public static class Builder {

        private SimpleVerticalDividerItemDecoration decoration;
        private Context context;

        public Builder(Context context) {
            decoration = new SimpleVerticalDividerItemDecoration(context);
            this.context = context;
        }

        public Builder setLastItemHasDivider(boolean lastItemHasDivider) {
            decoration.lastItemHasDivider = lastItemHasDivider;
            return this;
        }

        public Builder setHeightRes(@DimenRes int heightRes) {
            decoration.height = context.getResources().getDimensionPixelOffset(heightRes);
            return this;
        }

        public Builder setColorRes(@ColorRes int colorRes) {
            decoration.divider.setColor(ContextCompat.getColor(context, colorRes));
            return this;
        }

        public Builder setFirstItemHasTopDivider(boolean firstItemHasTopDivider) {
            decoration.firstItemHasTopDivider = firstItemHasTopDivider;
            return this;
        }

        public Builder setLeftMargin(@DimenRes int leftMarginRes) {
            decoration.leftMargin = context.getResources().getDimensionPixelOffset(leftMarginRes);
            return this;
        }

        public Builder setRightMargin(@DimenRes int rightMarginRes) {
            decoration.rightMargin = context.getResources().getDimensionPixelOffset(rightMarginRes);
            return this;
        }

        public Builder setTopPadding(@DimenRes int padding) {
            decoration.topPadding = context.getResources().getDimensionPixelOffset(padding);
            return this;
        }

        public Builder setBottomPadding(@DimenRes int padding) {
            decoration.bottomPadding = context.getResources().getDimensionPixelOffset(padding);
            return this;
        }

        public Builder addPaddingAboveFirstItem(boolean shouldAddPadding) {
            decoration.paddingAboveFirstItem = shouldAddPadding;
            return this;
        }

        public Builder addPaddingBelowLastItem(boolean shouldAddPadding) {
            decoration.paddingBelowLastItem = shouldAddPadding;
            return this;
        }

        public Builder setFirstItemPosition(int firstItemPosition) {
            decoration.firstItemPosition = firstItemPosition;
            return this;
        }

        public SimpleVerticalDividerItemDecoration build() {
            return decoration;
        }
    }
}
