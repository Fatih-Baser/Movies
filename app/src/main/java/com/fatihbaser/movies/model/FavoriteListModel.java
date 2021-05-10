package com.fatihbaser.movies.model;

public class FavoriteListModel {

    String title;
    String posterpath;

    public FavoriteListModel(String title, String posterpath){
        this.title = title;
        this.posterpath = posterpath;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterpath() {
        return posterpath;
    }

}