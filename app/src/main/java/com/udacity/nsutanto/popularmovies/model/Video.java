package com.udacity.nsutanto.popularmovies.model;

public class Video {

    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_THUMBNAIL_PRE = "http://img.youtube.com/vi/";
    private static final String YOUTUBE_THUMBNAIL_POST = "/0.jpg";

    private String id;

    public Video(String id) {
        this.id = id;
    }

    public String getYouTubeURL() {
        return YOUTUBE_URL + id;
    }

    public String getYouTubeThumbnailURL() {
        return YOUTUBE_THUMBNAIL_PRE + id + YOUTUBE_THUMBNAIL_POST;
    }
}