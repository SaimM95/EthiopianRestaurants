package com.example.abc.ethiopianrestaurants.features.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abc.ethiopianrestaurants.R;
import com.example.abc.ethiopianrestaurants.model.Review;
import com.example.abc.ethiopianrestaurants.model.User;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsListAdapter extends ListAdapter<Review, ReviewsListAdapter.ReviewViewHolder> {

    public ReviewsListAdapter() {
        super(new DiffUtil.ItemCallback<Review>() {
            @Override
            public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
                return oldItem.id.equals(newItem.id);
            }
        });
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review_item,
            parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = getItem(position);
        holder.bindView(review);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProfileImage;
        private TextView tvName;
        private AppCompatRatingBar ratingBar;
        private TextView tvDate;
        private TextView tvReviewText;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.review_item_profile_image);
            tvName = itemView.findViewById(R.id.review_item_profile_name);
            ratingBar = itemView.findViewById(R.id.review_item_rating_bar);
            tvDate = itemView.findViewById(R.id.review_item_date);
            tvReviewText = itemView.findViewById(R.id.review_item_text);
        }

        void bindView(Review review) {
            User user = review.user;
            Picasso.with(itemView.getContext())
                .load(user.imageUrl)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_error)
                .into(ivProfileImage);

            tvName.setText(user.name);
            ratingBar.setRating((float)review.rating);
            tvDate.setText(review.timeCreated);
            tvReviewText.setText(review.text);
        }
    }
}
