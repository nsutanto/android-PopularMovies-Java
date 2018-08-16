package com.udacity.nsutanto.popularmovies.task;

import android.os.AsyncTask;
import com.udacity.nsutanto.popularmovies.listener.ITaskMovieListener;
import com.udacity.nsutanto.popularmovies.model.Movie;
import com.udacity.nsutanto.popularmovies.utils.JsonUtils;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class FetchMovieTask extends AsyncTask<ITaskMovieListener, Void, ArrayList<Movie>> {

    private ITaskMovieListener mTaskListener;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(ITaskMovieListener... taskListeners) {

        String jsonResponse;
        mTaskListener = taskListeners[0];
        URL url = mTaskListener.GetMovieURL();

        try {
            jsonResponse = NetworkUtils.GetResponseFromHttpUrl(url);
            ArrayList<Movie> movies = JsonUtils.ParseMoviesJSON(jsonResponse);
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        mTaskListener.OnPostExecute(movies);
    }
}