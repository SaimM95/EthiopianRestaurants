package com.example.abc.ethiopianrestaurants.features.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.common.ListItem;
import com.example.abc.ethiopianrestaurants.model.Business;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Map<String, Integer> categoryCountMap = new HashMap<>();
    private List<ListItem> listItems = new ArrayList<>();
    private OnSearchResultClickedListener clickListener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItem.ViewType listItemViewType = ListItem.ViewType.values()[viewType];
        if (listItemViewType == ListItem.ViewType.CATEGORY) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_search_result_category, parent, false);
            return new CategoryViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_search_result_business, parent, false);
            return new BusinessViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object value = listItems.get(position).value;
        if (holder instanceof CategoryViewHolder && value instanceof String) {
            ((CategoryViewHolder)holder).bindView((String)value);
        } else if (holder instanceof BusinessViewHolder && value instanceof Business) {
            ((BusinessViewHolder)holder).bindView((Business)value);
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).viewType.ordinal();
    }

    void updateItems(List<ListItem> listItems, Map<String, Integer> categoryCountMap) {
        this.listItems = listItems;
        this.categoryCountMap = categoryCountMap;
        notifyDataSetChanged();
    }

    void setOnSearchResultClickedListener(OnSearchResultClickedListener clickListener) {
        this.clickListener = clickListener;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.search_result_category_name);
        }

        void bindView(String categoryName) {
            if (categoryCountMap.containsKey(categoryName)) {
                String text = itemView.getContext().getString(R.string.search_result_category,
                    categoryName, categoryCountMap.get(categoryName));
                tvName.setText(text);
            }
        }
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.search_result_business_name);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position >= 0) {
                    Object value = listItems.get(position).value;
                    if (clickListener != null
                        && value instanceof Business) {
                        clickListener.onSearchResultClicked((Business)value);
                        String toastText =
                            itemView.getContext().getString(R.string.search_result_clicked_toast,
                                ((Business)value).name);
                        Toast.makeText(itemView.getContext(), toastText, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        void bindView(Business business) {
            tvName.setText(business.name);
        }
    }

    interface OnSearchResultClickedListener {
        void onSearchResultClicked(Business business);
    }
}
