package com.fatihbaser.movies.service;


import com.fatihbaser.movies.model.MovieDetailsModel;
import com.fatihbaser.movies.model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //https://api.themoviedb.org/3/movie/popular?
    //
    // api_key=1bf3c5469807a4b8cb7a0a8a888014b0

    @GET("/3/movie/{category}")
    Call<MovieModel> getMovies(
            @Path("category") String category,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/search/movie?api_key=1bf3c5469807a4b8cb7a0a8a888014b0&query=türkish+ice+cream


    @GET("/3/search/{category}")
    Call<MovieModel> search(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("query") String searchWord,
            @Query("page") int page
    );


    //https://api.themoviedb.org/3/movie/popular?api_key=1bf3c5469807a4b8cb7a0a8a888014b0&page=1


    @GET("/3/movie/{category}")
    Call<MovieModel> getPages(
      @Path("category") String category,
      @Query("api_key") String api_key,
      @Query("page") int page
    );



    //https://api.themoviedb.org/3/movie/791373?api_key=1bf3c5469807a4b8cb7a0a8a888014b0

    @GET("/3/movie/{movie_id}")
    Call<MovieDetailsModel> getDetails(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );






    /*
    @GET("/3/movie/{movie_id}")
    Call<MovieModel> getDetails(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

     */

}
