package com.example.iti_mvc.model;

import androidx.lifecycle.LiveData;

import com.example.iti_mvc.network.NetworkDelegate;

import java.util.List;

public interface RepositoryInterface {

    public void deleteMovie(Movies movie);

    public void insertMovie(Movies movie);

    public LiveData<List<Movies>> getFavMovies();

    public LiveData<List<Movies>> getAllMovies(NetworkDelegate networkDelegate);
}
