package com.udacity.nsutanto.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    private String author;
    private String content;

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {

        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() { return author; }

    public String getContent() { return content; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(author);
        out.writeString(content);
    }

    private Review(Parcel in) {
        author = in.readString();
        content = in.readString();
    }
}