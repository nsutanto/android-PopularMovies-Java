package com.udacity.nsutanto.popularmovies.listener;
import com.udacity.nsutanto.popularmovies.model.Review;

import java.net.URL;
import java.util.ArrayList;


public interface ITaskReviewListener {
    void OnPostExecute(ArrayList<Review> reviews);
    URL GetReviewURL();
}