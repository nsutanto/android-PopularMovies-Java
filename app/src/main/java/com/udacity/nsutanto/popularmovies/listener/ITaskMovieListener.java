package com.udacity.nsutanto.popularmovies.listener;

import com.udacity.nsutanto.popularmovies.model.Movie;

import java.net.URL;
import java.util.ArrayList;

public interface ITaskMovieListener {
    void OnPostExecute(ArrayList<Movie> movies);
    URL GetMovieURL();
    void StartDetailActivity(Movie movie);
}
