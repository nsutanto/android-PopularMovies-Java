package com.udacity.nsutanto.popularmovies.task;

import android.os.AsyncTask;

import com.udacity.nsutanto.popularmovies.listener.ITaskReviewListener;
import com.udacity.nsutanto.popularmovies.model.Review;
import com.udacity.nsutanto.popularmovies.utils.JsonUtils;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class FetchReviewTask extends AsyncTask<ITaskReviewListener, Void, ArrayList<Review>> {

    private ITaskReviewListener mTaskListener;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected ArrayList<Review> doInBackground(ITaskReviewListener... taskListeners) {

        String jsonResponse;
        mTaskListener = taskListeners[0];
        URL url = mTaskListener.GetReviewURL();

        try {
            jsonResponse = NetworkUtils.GetResponseFromHttpUrl(url);
            ArrayList<Review> reviews = JsonUtils.ParseReviewsJSON(jsonResponse);
            return reviews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        mTaskListener.OnPostExecute(reviews);
    }
}
