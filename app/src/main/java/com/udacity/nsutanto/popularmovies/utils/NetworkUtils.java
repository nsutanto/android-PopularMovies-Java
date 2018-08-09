package com.udacity.nsutanto.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w185";
    public static final String BASE_URL = "https://api.themoviedb.org/3";
    public static final String API_MOST_POPULAR = "/movie/popular";
    public static final String API_TOP_RATED = "/movie/top_rated";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VALUE = "9bb95d3d5e8e658e1a25ef4a12f3a088";

    public static URL BuildPopularMovieURL() {
        Uri popularMovieUri = Uri.parse(BASE_URL + API_MOST_POPULAR).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .build();

        try {
            URL popularMovieUrl = new URL(popularMovieUri.toString());
            Log.v(TAG, "URL: " + popularMovieUrl);
            return popularMovieUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL BuildTopRatedMovieURL() {
        Uri popularMovieUri = Uri.parse(BASE_URL + API_TOP_RATED).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .build();

        try {
            URL popularMovieUrl = new URL(popularMovieUri.toString());
            Log.v(TAG, "URL: " + popularMovieUrl);
            return popularMovieUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String GetResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
