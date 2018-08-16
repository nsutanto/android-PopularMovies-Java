package com.udacity.nsutanto.popularmovies.task;

import android.os.AsyncTask;

import com.udacity.nsutanto.popularmovies.listener.ITaskVideoListener;
import com.udacity.nsutanto.popularmovies.model.Video;
import com.udacity.nsutanto.popularmovies.utils.JsonUtils;
import com.udacity.nsutanto.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;


public class FetchVideoTask extends AsyncTask<ITaskVideoListener, Void, ArrayList<Video>> {

    private ITaskVideoListener mTaskListener;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected ArrayList<Video> doInBackground(ITaskVideoListener... taskListeners) {

        String jsonResponse;
        mTaskListener = taskListeners[0];
        URL url = mTaskListener.GetVideoURL();

        try {
            jsonResponse = NetworkUtils.GetResponseFromHttpUrl(url);
            ArrayList<Video> videos = JsonUtils.ParseVideosJSON(jsonResponse);
            return videos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Video> videos) {
        mTaskListener.OnPostExecuteVideoTask(videos);
    }
}