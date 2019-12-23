package com.example.abc.ethiopianrestaurants.features.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_CATEGORY = 1;
    private static final int VIEW_TYPE_BUSINESS = 2;

    private Set<String> categorySet = new HashSet<>();
    private List<String> categorizedBusinesses = new ArrayList<>();
    private Map<String, Integer> categoryCountMap = new HashMap<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CATEGORY) {
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
        String name = categorizedBusinesses.get(position);
        if (holder instanceof CategoryViewHolder) {
            ((CategoryViewHolder)holder).bindView(name);
        } else if (holder instanceof BusinessViewHolder) {
            ((BusinessViewHolder)holder).bindView(name);
        }
    }

    @Override
    public int getItemCount() {
        return categorizedBusinesses.size();
    }

    @Override
    public int getItemViewType(int position) {
        String item = categorizedBusinesses.get(position);
        if (categorySet.contains(item)) {
            return VIEW_TYPE_CATEGORY;
        }
        return VIEW_TYPE_BUSINESS;
    }

    void updateItems(Set<String> categorySet, List<String> categorizedBusinesses,
        Map<String, Integer> categoryCountMap) {
        this.categorySet = categorySet;
        this.categorizedBusinesses = categorizedBusinesses;
        this.categoryCountMap = categoryCountMap;
        notifyDataSetChanged();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.search_result_category_name);
        }

        void bindView(String categoryName) {
            if (categoryCountMap.containsKey(categoryName)) {
                String text = String.format(Locale.CANADA, "%s (%d)", categoryName,
                    categoryCountMap.get(categoryName));
                tvName.setText(text);
            }
        }
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.search_result_business_name);
        }

        void bindView(String businessName) {
            tvName.setText(businessName);
        }
    }
}
