package com.udacity.nsutanto.popularmovies;

import android.arch.persistence.room.ColumnInfo;
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
import com.udacity.nsutanto.popularmovies.model.AppDatabase;
import com.udacity.nsutanto.popularmovies.model.Movie;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mImageView;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mOverview;
    private AppDatabase mAppDatabase;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        mMovie = intent.getParcelableExtra("movie");

        initUI();

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
    }

    private void initUI() {
        mTitle = findViewById(R.id.tv_title);
        mImageView = findViewById(R.id.poster);
        mReleaseDate = findViewById(R.id.tv_date);
        mVoteAverage = findViewById(R.id.tv_vote);
        mOverview = findViewById(R.id.tv_overview);
    }

    public void OnFavButtonClick(View v) {

        if (mMovie.getFavorite() == 0) {
            mMovie.setFavorite(1);
            mAppDatabase.movieDao().insertMovie(mMovie);
        } else {
            mAppDatabase.movieDao().deleteMovie(mMovie);
            mMovie.setFavorite(0);
        }

        finish();
    }
}
