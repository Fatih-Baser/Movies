package com.fatihbaser.movies.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatihbaser.movies.R;
import com.fatihbaser.movies.ViewModel.HomeFragmentViewModel;
import com.fatihbaser.movies.activity.MovieDetailsActivity;
import com.fatihbaser.movies.adapter.HomeAdapter;
import com.fatihbaser.movies.model.MovieModel;
import com.fatihbaser.movies.service.ApiService;
import com.fatihbaser.movies.service.RetroIntance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements HomeAdapter.ItemClickListener {
    FloatingActionButton backPage;
    FloatingActionButton nextPage;
    RecyclerView recyclerView;
    int pos =-1;


    public String BASE_URL = "https://api.themoviedb.org";
    public static final String BASE_PHOTO_URL = "https://image.tmdb.org/t/p/w500";
    public String API_KEY = "1bf3c5469807a4b8cb7a0a8a888014b0";
    public Retrofit retrofit;

    public ArrayList<MovieModel.Results> movieResults;
    public static HomeFragmentViewModel viewModel;

    int page=1;
    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        backPage = v.findViewById(R.id.backPage);
        nextPage = v.findViewById(R.id.nextPage);
        recyclerView = v.findViewById(R.id.recyclerView);



        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPage();
            }
        });


        loadMovies(page);


        return v;
    }



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
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    HomeAdapter adapter = new HomeAdapter(movieResults,getContext(), movie -> HomeFragment.this.onMovieClick(movie));

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(getContext(), "Data couldn't load!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void nextPage(){
        if(page < 0 ){
            Toast.makeText(getContext(), "Last Page!", Toast.LENGTH_SHORT).show();
        } else{
            page = page + 1 ;
            loadMovies(page);
        }
    }

    public void backPage(){
        if(page < 0 || page == 1){
            Toast.makeText(getContext(), "Last Page!", Toast.LENGTH_SHORT).show();
        } else{
            page = page - 1;
            if(page == 1){
                loadMovies(page);
            } else{
                loadMovies(page);
            }
        }
    }


    @Override
    public void onMovieClick(MovieModel.Results movie) {

        Intent intent1 = new Intent(getContext(), MovieDetailsActivity.class);
        intent1.putExtra("movie_id",movie.getMovieId());
        getContext().startActivity(intent1);

    }
}

