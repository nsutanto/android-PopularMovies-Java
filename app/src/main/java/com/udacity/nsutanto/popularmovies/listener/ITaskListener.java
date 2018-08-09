package com.udacity.nsutanto.popularmovies.listener;

import com.udacity.nsutanto.popularmovies.model.Movie;

import java.net.URL;
import java.util.ArrayList;

public interface ITaskListener {
    void OnPostExecute(ArrayList<Movie> movies);
    URL GetURL();
    void StartDetailActivity(Movie movie);
}
