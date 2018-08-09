package com.udacity.nsutanto.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.nsutanto.popularmovies.adapter.MovieAdapter;
import com.udacity.nsutanto.popularmovies.listener.ITaskListener;
import com.udacity.nsutanto.popularmovies.model.Movie;
import com.udacity.nsutanto.popularmovies.task.FetchMovieTask;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ITaskListener {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessage;
    private SortBy mSortBy;
    private enum SortBy {
        POPULAR, TOP_RATED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initRecyclerView();

        loadMovieData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            mSortBy = SortBy.POPULAR;
            loadMovieData();
        }
        else {
            mSortBy = SortBy.TOP_RATED;
            loadMovieData();
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnPostExecute(ArrayList<Movie> movies) {

        if (movies.isEmpty()) {

        } else {
            mErrorMessage.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mMovieAdapter.setMovies(movies);
        }
    }

    public URL GetURL() {
        if (mSortBy == SortBy.TOP_RATED) {
            return NetworkUtils.BuildTopRatedMovieURL();
        } else {
            return NetworkUtils.BuildPopularMovieURL();
        }
    }

    public void StartDetailActivity(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    private void loadMovieData() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.isConnectedOrConnecting()) {
            mErrorMessage.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.VISIBLE);
            new FetchMovieTask().execute(this);
        } else {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText("Fail to fetch movies");
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    private void initUI() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSortBy = SortBy.POPULAR;
        mLoadingIndicator = findViewById(R.id.progressBar);
        mErrorMessage = findViewById(R.id.errorTextView);

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView_movie);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

    }
}
