package com.fatihbaser.movies.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatihbaser.movies.R;
import com.fatihbaser.movies.ViewModel.MovieDataViewModel;
import com.fatihbaser.movies.adapter.FavoritesAdapter;
import com.fatihbaser.movies.database.MovieData;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {


    ImageView listTitleImage;
    RecyclerView favoritesRecycler;

    List<MovieData> dataList = new ArrayList<>();
    FavoritesAdapter adapter;
    public static MovieDataViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorite,container,false);
        listTitleImage = v.findViewById(R.id.listTitleImage);
        favoritesRecycler = v.findViewById(R.id.favoritesListRecyclerView);

        favoritesRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));

        favoritesRecycler.setHasFixedSize(true);

        adapter = new FavoritesAdapter(v.getContext(),dataList);

        favoritesRecycler.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MovieDataViewModel.class);

        viewModel.getListLiveData().observe(this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(List<MovieData> movieData) {
                adapter.setDataList(movieData);
            }
        });

        return v;
    }
}