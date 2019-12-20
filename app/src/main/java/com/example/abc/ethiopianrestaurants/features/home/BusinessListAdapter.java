package com.example.abc.ethiopianrestaurants.features.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class BusinessListAdapter
    extends ListAdapter<Business, BusinessListAdapter.BusinessViewHolder> {

    @Nullable private OnBusinessItemClickedListener itemClickedListener;

    BusinessListAdapter() {
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

    void setItemClickedListener(@Nullable OnBusinessItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivBusinessImg;
        private TextView tvBusinessName;
        private AppCompatRatingBar ratingBar;
        private TextView tvReviews;

        BusinessViewHolder(@NonNull View itemView) {
            super(itemView);

            ivBusinessImg = itemView.findViewById(R.id.business_card_iv_business_img);
            tvBusinessName = itemView.findViewById(R.id.business_card_tv_business_name);
            ratingBar = itemView.findViewById(R.id.business_card_rating_bar);
            tvReviews = itemView.findViewById(R.id.business_card_tv_reviews);

            itemView.setOnClickListener(v -> {
                if (itemClickedListener != null) {
                    Business business = getItem(getAdapterPosition());
                    Timber.d("opening details for:%s", business.name);
                    itemClickedListener.onClicked(business);
                }
            });
        }

        void bindView(Business business) {
            Context context = itemView.getContext();
            tvBusinessName.setText(business.name);
            ratingBar.setRating((float)business.rating);
            tvReviews.setText(context.getString(R.string.reviews_count_text, business.reviewCount));

            Picasso.with(context).load(business.imageUrl)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_error)
                .into(ivBusinessImg);
        }
    }

    interface OnBusinessItemClickedListener {
        void onClicked(Business business);
    }
}
