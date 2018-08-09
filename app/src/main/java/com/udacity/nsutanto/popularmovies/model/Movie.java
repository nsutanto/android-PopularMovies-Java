package com.udacity.nsutanto.popularmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private final int mId;
    private final String mTitle;
    private final String mReleaseDate;
    private final String mPosterPath;
    private final String mVoteAverage;
    private final String mOverview;

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(int id, String title, String releaseDate, String posterPath, String voteAverage, String overview) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
        mVoteAverage = voteAverage;
        mOverview = overview;
    }

    public String GetTitle() { return mTitle; }

    public String GetReleaseDate() { return mReleaseDate; }

    public String GetPosterPath() { return mPosterPath; }

    public String GetVoteAverage() { return mVoteAverage; }

    public String GetOverview() { return mOverview; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mTitle);
        out.writeString(mReleaseDate);
        out.writeString(mPosterPath);
        out.writeString(mVoteAverage);
        out.writeString(mOverview);
    }

    private Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mPosterPath = in.readString();
        mVoteAverage = in.readString();
        mOverview = in.readString();
    }
}