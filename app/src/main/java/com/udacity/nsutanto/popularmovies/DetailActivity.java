package com.udacity.nsutanto.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.nsutanto.popularmovies.adapter.ReviewAdapter;
import com.udacity.nsutanto.popularmovies.listener.ITaskReviewListener;
import com.udacity.nsutanto.popularmovies.model.AppDatabase;
import com.udacity.nsutanto.popularmovies.model.Movie;
import com.udacity.nsutanto.popularmovies.model.Review;
import com.udacity.nsutanto.popularmovies.task.FetchReviewTask;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements ITaskReviewListener {

    private TextView mTitle;
    private ImageView mImageView;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mOverview;
    private Button mFavorite;
    private AppDatabase mAppDatabase;
    private RecyclerView mRVReview;
    private ReviewAdapter mReviewAdapter;
    private LinearLayoutManager mReviewLayoutManager;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        mMovie = intent.getParcelableExtra("movie");

        initUI();
        initReviewRecyclerView();

        mTitle.setText(mMovie.getTitle());

        String posterPath = mMovie.getPosterPath();

        if (!posterPath.isEmpty()) {
            Picasso.get()
                    .load(NetworkUtils.BASE_URL_POSTER + mMovie.getPosterPath())
                    .into(mImageView);
        }

        String releaseDate = mMovie.getReleaseDate();

        if (releaseDate.length() > 4) {
            releaseDate.substring(0, 4);
        }
        mReleaseDate.setText(releaseDate);

        String voteAverage = mMovie.getVoteAverage();
        mVoteAverage.setText(voteAverage);
        mOverview.setText(mMovie.getOverview());

        if (mMovie.getFavorite() == 0) {
            mFavorite.setText("Mark as favorite");
        } else {
            mFavorite.setText("Favorite");
        }

        loadReviews();
    }

    private void initUI() {
        mTitle = findViewById(R.id.tv_title);
        mImageView = findViewById(R.id.poster);
        mReleaseDate = findViewById(R.id.tv_date);
        mVoteAverage = findViewById(R.id.tv_vote);
        mOverview = findViewById(R.id.tv_overview);
        mFavorite = findViewById(R.id.buttonFavorite);
    }

    public URL GetReviewURL() {
        URL reviewURL = NetworkUtils.BuildReviewsURL(String.valueOf(mMovie.getId()));
        return reviewURL;
    }

    public void OnPostExecute(ArrayList<Review> reviews) {
        if (!reviews.isEmpty()) {
            mReviewAdapter.setReviews(reviews);
        }
    }

    public void OnFavButtonClick(View v) {

        if (mMovie.getFavorite() == 0) {
            mFavorite.setText("Favorite");
            mMovie.setFavorite(1);

            AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mAppDatabase.movieDao().insertMovie(mMovie);
                }
            });

        } else {
            AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mAppDatabase.movieDao().deleteMovie(mMovie);
                }
            });
            mFavorite.setText("Mark as favorite");
            mMovie.setFavorite(0);
        }
    }

    private void initReviewRecyclerView() {
        mRVReview = findViewById(R.id.rv_review);

        mReviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewLayoutManager.setSmoothScrollbarEnabled(true);
        mRVReview.setLayoutManager(mReviewLayoutManager);
        mRVReview.setNestedScrollingEnabled(false);
        mRVReview.setHasFixedSize(true);
        mReviewAdapter = new ReviewAdapter();
        mRVReview.setAdapter(mReviewAdapter);

    }

    private void loadReviews() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.isConnectedOrConnecting()) {
            new FetchReviewTask().execute(this);
        } else {

        }
    }
}
