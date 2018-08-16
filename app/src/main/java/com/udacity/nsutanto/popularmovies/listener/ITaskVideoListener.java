package com.udacity.nsutanto.popularmovies.listener;
import com.udacity.nsutanto.popularmovies.model.Video;

import java.net.URL;
import java.util.ArrayList;

public interface ITaskVideoListener {
    void PlayVideo(Video video);
    void OnPostExecuteVideoTask(ArrayList<Video> videos);
    URL GetVideoURL();
}