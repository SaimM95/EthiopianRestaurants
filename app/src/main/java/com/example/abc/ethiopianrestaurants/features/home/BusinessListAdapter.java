package com.example.abc.ethiopianrestaurants.features.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.model.Business;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BusinessListAdapter
    extends ListAdapter<Business, BusinessListAdapter.BusinessViewHolder> {

    private static final String TAG = BusinessListAdapter.class.getCanonicalName();

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
        }

        void bindView(Business business) {
            tvBusinessName.setText(business.name);
            ratingBar.setRating((float)business.rating);
            tvReviews.setText(business.reviewCount + " reviews");

            Log.d(TAG, "Loading image");
            Picasso picasso = new Picasso.Builder(itemView.getContext()).build();

            picasso.setLoggingEnabled(true);
            picasso.load(business.imageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_error)
                .into(ivBusinessImg);
        }
    }
}
