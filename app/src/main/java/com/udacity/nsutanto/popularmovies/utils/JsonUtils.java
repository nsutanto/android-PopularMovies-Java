package com.udacity.nsutanto.popularmovies.utils;

import com.udacity.nsutanto.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static final String RESULTS = "results";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String OVERVIEW = "overview";


    public static ArrayList<Movie> ParseMoviesJSON(String json) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(json);

        JSONArray results = jsonObj.getJSONArray(RESULTS);

        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);

            int id = movie.getInt(ID);
            String title = movie.getString(TITLE);
            String releaseDate = movie.getString(RELEASE_DATE);
            String overview = movie.getString(OVERVIEW);
            String posterPath = movie.getString(POSTER_PATH);
            String voteAverage = movie.getString(VOTE_AVERAGE);

            Movie movieData = new Movie(id, title, releaseDate, posterPath, voteAverage, overview);
            movies.add(movieData);
        }

        return movies;
    }
}

