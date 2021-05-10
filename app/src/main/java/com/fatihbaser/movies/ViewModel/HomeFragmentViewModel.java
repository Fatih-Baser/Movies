package com.fatihbaser.movies.ViewModel;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatihbaser.movies.model.MovieModel;
import com.fatihbaser.movies.service.ApiService;
import com.fatihbaser.movies.service.RetroIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragmentViewModel extends ViewModel {

    public String BASE_URL = "https://api.themoviedb.org";
    public static final String BASE_PHOTO_URL = "https://image.tmdb.org/t/p/w500";
    public String API_KEY = "1bf3c5469807a4b8cb7a0a8a888014b0";
    public Retrofit retrofit;
    public static int page = 1;

    public ArrayList<MovieModel.Results> movieResults;

    public void loadMovies(int page){

        ApiService apiService = RetroIntance.getRetroClient().create(ApiService.class);

        Call<MovieModel> modelC = apiService.getPages("popular",API_KEY,page);

        modelC.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if(response.isSuccessful()){

                    MovieModel movieModel = response.body();
                    List<MovieModel.Results> list = movieModel.getResults();
                    movieResults = new ArrayList<>(list);



                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                //Toast.makeText(getContext(), "Data couldn't load!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void nextPage(){
        if(page < 0 ){
           // Toast.makeText(getContext(), "Last Page!", Toast.LENGTH_SHORT).show();
        } else{
            page = page + 1 ;
            loadMovies(page);
        }
    }

    public void backPage(){
        if(page < 0 || page == 1){
          //  Toast.makeText(getContext(), "Last Page!", Toast.LENGTH_SHORT).show();
        } else{
            page = page - 1;
            if(page == 1){
                loadMovies(page);
            } else{
                loadMovies(page);
            }
        }
    }
}
