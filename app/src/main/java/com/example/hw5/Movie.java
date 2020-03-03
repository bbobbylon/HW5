package com.example.hw5;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String title;
    private String vote_average;
    private String popularity;
    private String overview;
    private String releaseDate;

    public Movie(String title, String vote_average, String popularity, String overview, String releaseDate) {
        this.title = title;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.overview = overview;
        this.releaseDate = releaseDate;



    }

    public Movie(String title) {

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String description) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    protected Movie(Parcel in) {
        title = in.readString();
        vote_average = in.readString();
        popularity = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(vote_average);
        dest.writeString(popularity);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @SuppressWarnings("unused")
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
}

