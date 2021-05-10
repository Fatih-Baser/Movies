package com.fatihbaser.movies.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.fatihbaser.movies.database.MovieData;
import com.fatihbaser.movies.database.MovieDataRepository;
import com.fatihbaser.movies.database.RoomDB;

import java.util.List;

public class MovieDataViewModel extends AndroidViewModel {

    private MovieDataRepository repository;
    private LiveData<List<MovieData>> listLiveData;
    private RoomDB database;

    public MovieDataViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieDataRepository(application);
        listLiveData = repository.getListLiveData();
        database = RoomDB.getInstance(application);
    }

    public void insert(MovieData movieData){
        repository.insert(movieData);
    }

    public void delete(MovieData movieData){
        repository.delete(movieData);
    }

    public LiveData<List<MovieData>> getListLiveData(){
        return listLiveData;
    }

    public int getDatabaseMovie(int id){
        return repository.getDatabaseMovie(id);
    }
}
