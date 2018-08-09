package com.udacity.nsutanto.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.nsutanto.popularmovies.model.Movie;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mImageView;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");

        mTitle = findViewById(R.id.tv_title);
        mTitle.setText(movie.GetTitle());


        mImageView = findViewById(R.id.poster);
        String posterPath = movie.GetPosterPath();

        if (!posterPath.isEmpty()) {
            Picasso.get()
                    .load(NetworkUtils.BASE_URL_POSTER + movie.GetPosterPath())
                    .into(mImageView);
        }

        mReleaseDate = findViewById(R.id.tv_date);

        String releaseDate = movie.GetReleaseDate();

        if (releaseDate.length() > 4) {
            releaseDate.substring(0, 4);
        }
        mReleaseDate.setText(releaseDate);

        mVoteAverage = findViewById(R.id.tv_vote);
        String voteAverage = movie.GetVoteAverage();
        mVoteAverage.setText(voteAverage);

        mOverview = findViewById(R.id.tv_overview);
        mOverview.setText(movie.GetOverview());
    }
}
