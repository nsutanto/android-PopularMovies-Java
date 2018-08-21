package com.udacity.nsutanto.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.nsutanto.popularmovies.adapter.MovieAdapter;
import com.udacity.nsutanto.popularmovies.listener.ITaskMovieListener;
import com.udacity.nsutanto.popularmovies.model.AppDatabase;
import com.udacity.nsutanto.popularmovies.model.Movie;
import com.udacity.nsutanto.popularmovies.task.FetchMovieTask;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;
import com.udacity.nsutanto.popularmovies.viewmodel.MovieViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


// TODO: Implement the Up back
// TODO: Implement keep loading the background when rotating the device

public class MainActivity extends AppCompatActivity implements ITaskMovieListener {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessage;
    private Toolbar mToolbar;
    private SortBy mSortBy = SortBy.POPULAR;
    private AppDatabase mAppDatabase;
    private List<Movie> mFavoriteMovies = new ArrayList<>();
    private enum SortBy {
        POPULAR, TOP_RATED, FAVORITE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null) {
            int sortByInt = savedInstanceState.getInt("SortBy");
            mSortBy = SortBy.values()[sortByInt];
        }

        initUI();
        initRecyclerView();

        if (mSortBy == SortBy.POPULAR || mSortBy == SortBy.TOP_RATED) {
            loadMovieData();
        }
        setupFavMovieVM();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);

        if (mSortBy == SortBy.POPULAR) {
            mToolbar.setTitle(R.string.action_popular);
        } else if (mSortBy == SortBy.TOP_RATED) {
            mToolbar.setTitle(R.string.action_top_rated);
        } else {
            mToolbar.setTitle(R.string.action_favorite);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            mSortBy = SortBy.POPULAR;
            mToolbar.setTitle(R.string.action_popular);
            loadMovieData();
        }
        else if (id == R.id.action_top_rated) {
            mSortBy = SortBy.TOP_RATED;
            mToolbar.setTitle(R.string.action_top_rated);
            loadMovieData();
        } else {
            // Favorite
            mSortBy = SortBy.FAVORITE;
            mToolbar.setTitle(R.string.action_favorite);
            mMovieAdapter.setMovies(mFavoriteMovies);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SortBy", mSortBy.ordinal());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int sortByInt = savedInstanceState.getInt("SortBy");
        mSortBy = SortBy.values()[sortByInt];
    }

    public void OnPostExecute(ArrayList<Movie> movies) {

        if (movies.isEmpty()) {

        } else {
            mErrorMessage.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mMovieAdapter.setMovies(movies);
        }
    }

    public URL GetMovieURL() {
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

    private void setupFavMovieVM() {

        MovieViewModel movieVM = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieVM.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mFavoriteMovies = movies;
                if (mSortBy == SortBy.FAVORITE) {
                    mMovieAdapter.setMovies(mFavoriteMovies);
                }
            }
        });
    }

    private void initUI() {

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mLoadingIndicator = findViewById(R.id.progressBar);
        mErrorMessage = findViewById(R.id.errorTextView);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView_movie);

        int numColumn = calculateNoOfColumns(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, numColumn);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
