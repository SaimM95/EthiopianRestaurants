package com.example.abc.ethiopianrestaurants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.model.Business;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BusinessListAdapter
    extends ListAdapter<Business, BusinessListAdapter.BusinessViewHolder> {

    protected BusinessListAdapter() {
        super(new DiffUtil.ItemCallback<Business>() {
            @Override
            public boolean areItemsTheSame(@NonNull Business oldItem, @NonNull Business newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Business oldItem,
                @NonNull Business newItem) {
                return oldItem.id.equals(newItem.id);
            }
        });
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_business_card,
            parent, false);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {
        Business business = getItem(position);
        holder.bindView(business);
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBusinessName;

        BusinessViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBusinessName = itemView.findViewById(R.id.tv_business_card_business_name);
        }

        void bindView(Business business) {
            tvBusinessName.setText(business.name);
        }
    }
}
