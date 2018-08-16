package com.udacity.nsutanto.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.udacity.nsutanto.popularmovies.R;
import com.udacity.nsutanto.popularmovies.model.Review;
import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> mReviews = new ArrayList<>();
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_content;
        private final TextView tv_author;

        public ViewHolder(View v) {
            super(v);
            tv_author = v.findViewById(R.id.tv_author);
            tv_content = v.findViewById(R.id.tv_content);
        }

        public void bind(Review review) {
            tv_author.setText(review.getAuthor());
            tv_content.setText(review.getContent());
        }
    }


    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
