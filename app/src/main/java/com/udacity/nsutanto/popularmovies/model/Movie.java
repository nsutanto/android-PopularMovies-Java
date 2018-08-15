package com.udacity.nsutanto.popularmovies.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "Movie")
public class Movie implements Parcelable {

    @PrimaryKey
    private final int id;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "release_date")
    private final String releaseDate;
    @ColumnInfo(name = "poster_path")
    private final String posterPath;
    @ColumnInfo(name = "vote_average")
    private final String voteAverage;
    @ColumnInfo(name = "overview")
    private final String overview;
    @ColumnInfo(name = "is_favorite")
    private int favorite;

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

    public Movie(int id, String title, String releaseDate, String posterPath, String voteAverage, String overview, int favorite) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.favorite = favorite;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getReleaseDate() { return releaseDate; }

    public String getPosterPath() { return posterPath; }

    public String getVoteAverage() { return voteAverage; }

    public String getOverview() { return overview; }

    public int getFavorite() { return favorite; }

    public void setFavorite(int value) { favorite = value; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(releaseDate);
        out.writeString(posterPath);
        out.writeString(voteAverage);
        out.writeString(overview);
        out.writeInt(favorite);
    }

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        favorite = in.readInt();
    }
}