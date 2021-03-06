package com.fatihbaser.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieModel {

    @SerializedName("results")
    public List<Results> results;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int total_page;

    @SerializedName("total_results")
    public int total_results;

    public List<Results> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public static class Results{

        @SerializedName("title")
        public String title;

        @SerializedName("id")
        public int movie_id;

        @SerializedName("release_date")
        public String release_date;

        @SerializedName("overview")
        public String overview;

        @SerializedName("poster_path")
        public String poster_path;

        public int getMovieId(){ return movie_id;}

        public String getTitle() {
            return title;
        }

        public String getRelease_date() {
            return release_date;
        }

        public String getOverview() {
            return overview;
        }

        public String getPoster_path(){
            return poster_path;
        }
    }

}